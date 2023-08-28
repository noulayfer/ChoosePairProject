package org.example.controller;

import org.example.DTO.TwoSubGroups;
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

public class AverageMarkCommand implements Command {
    StudentService2 studentService;
    public AverageMarkCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> pairOfStudent = studentService.getLastPair();
        ServletUtil.setCommonAttributes(req, pairOfStudent, studentService);
        if (!pairOfStudent.isEmpty()) {
            ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(pairOfStudent.get(0), studentService),
                    ServletUtil.getMark(pairOfStudent.get(1), studentService));
        }
        if (!studentService.isSaved()) {
            req.getRequestDispatcher("welcome-page.jsp").forward(req, resp);
        } else {
            ServletUtil.setAverage(req, studentService);
            req.setAttribute("line", " ---------------------- ");
            req.getRequestDispatcher("welcome-page.jsp").forward(req, resp);
        }
    }
}
