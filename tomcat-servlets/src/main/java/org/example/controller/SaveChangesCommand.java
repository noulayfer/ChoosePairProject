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

public class SaveChangesCommand implements Command {

    StudentService2 studentService;

    public SaveChangesCommand() {
        studentService = StudentService2.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> lastPair = studentService.getLastPair();
        if (lastPair.isEmpty() && studentService.getGroupOneCounter() == 0) {
            request.getRequestDispatcher("/controller?command=students")
                    .forward(request, response);
            return;
        }
        Student student1 = lastPair.get(0);
        Student student2 = lastPair.get(1);
        double mark1 = ServletUtil.getMark(student1, studentService);
        double mark2 = ServletUtil.getMark(student2, studentService);
        if (mark1 == 0 && mark2 == 0) {
            ServletUtil.setCommonAttributes(request, student1, student2,
                    studentService.getTwoSubGroups(), studentService.getDeletedUsers());
            ServletUtil.setCommonMarkAttributes(request, ServletUtil.getMark(student1, studentService),
                    ServletUtil.getMark(student2, studentService));

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome-page.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        studentService.saveBattlesLastPair();
        request.getRequestDispatcher("/controller?command=students")
                .forward(request, response);
    }
}

