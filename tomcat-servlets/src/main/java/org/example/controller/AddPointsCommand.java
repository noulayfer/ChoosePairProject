package org.example.controller;

import org.example.model.Battle;
import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddPointsCommand implements Command{
        StudentService2 studentService;
    public AddPointsCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }
        ServletUtil.updateMark(name, studentService, 0.5);
        List<Student> pairOfStudent = studentService.getLastPair();
        if (pairOfStudent.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }
        ServletUtil.setCommonAttributes(req, pairOfStudent, studentService);
        ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(pairOfStudent.get(0), studentService),
                ServletUtil.getMark(pairOfStudent.get(1), studentService));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
  }
}
