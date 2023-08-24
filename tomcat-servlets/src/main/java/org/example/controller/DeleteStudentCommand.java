package org.example.controller;

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

public class DeleteStudentCommand implements Command {

    StudentService2 studentService;
    public DeleteStudentCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        studentService.deleteByName(name);
        List<Student> lastPair = studentService.getLastPair();
        if (lastPair.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
        } else {
            ServletUtil.setCommonAttributes(req, lastPair, studentService);
            ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(lastPair.get(0), studentService),
                    ServletUtil.getMark(lastPair.get(1), studentService));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
