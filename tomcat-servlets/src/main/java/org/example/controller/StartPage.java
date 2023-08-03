package org.example.controller;

import org.example.DTO.TwoSubGroups;
import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Welcome", value = "/students")
public class StartPage extends HttpServlet {
    private StudentService studentService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TwoSubGroups twoSubGroups = studentService.getTwoSubGroups();
        req.setAttribute("firstGroup", twoSubGroups.getFirstSubGroup());
        req.setAttribute("secondGroup", twoSubGroups.getSecondSubGroup());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
