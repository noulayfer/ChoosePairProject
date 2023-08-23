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
            List<Student> pairOfStudent = studentService.getLastPair();
            Student student1 = pairOfStudent.get(0);
            Student student2 = pairOfStudent.get(1);

            ServletUtil.setCommonAttributes(req, student1, student2,
                    studentService.getTwoSubGroups(), studentService.getDeletedUsers());
            ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(student1, studentService),
                    ServletUtil.getMark(student2, studentService));

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
