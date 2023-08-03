package org.example.controller;

import org.example.DTO.TwoSubGroups;
import org.example.model.Student;
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
import java.util.List;

//TODO now this method returns names and 0.0 instead of marks. It is because of we do not put marks in DB.
@WebServlet(name = "FullStat", value = "/stat")
public class FullStatistic extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TwoSubGroups twoSubGroups = studentService.showFullStat();
        List<Student> firstSubGroup = twoSubGroups.getFirstSubGroup();
        List<Student> secondSubGroup = twoSubGroups.getSecondSubGroup();
        req.setAttribute("secondGroup", secondSubGroup);
        req.setAttribute("firstGroup", firstSubGroup);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("statistic.jsp");
        requestDispatcher.forward(req, resp);
    }
}
