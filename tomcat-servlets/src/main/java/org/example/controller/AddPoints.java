package org.example.controller;

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

@WebServlet(name="updateScore", value = "/update-score")
public class AddPoints extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || name.isEmpty()) {
            resp.sendRedirect("/tomcat/average");
            return;
        }
        Student studentByName = studentService.getStudentFromList(name);
        double mark = studentByName.getMark();
        studentByName.setMark(++mark);
        req.setAttribute("score", mark);
        List<Student> pairOfStudent = studentService.getLastPair();
        List<Student> firstSubGroup = studentService.getFirstSubGroup();
        List<Student> secondSubGroup = studentService.getSecondSubGroup();

        req.setAttribute("firstScore", pairOfStudent.get(0).getMark());
        req.setAttribute("secondScore", pairOfStudent.get(1).getMark());
        req.setAttribute("firstStudent", pairOfStudent.get(0));
        req.setAttribute("secondStudent", pairOfStudent.get(1));
        req.setAttribute("firstGroup", firstSubGroup);
        req.setAttribute("secondGroup", secondSubGroup);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
