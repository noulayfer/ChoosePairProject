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

public class SaveChangesCommand implements Command {

    StudentService2 studentService;

    public SaveChangesCommand() {
        studentService = StudentService2.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> lastPair = studentService.getLastPair();
        if (lastPair.isEmpty()) {
            request.getRequestDispatcher("/controller?command=students")
                    .forward(request, response);
            return;
        }
        studentService.saveBattlesLastPair();
//        ServletUtil.setCommonAttributes(request, lastPair, studentService);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(request, response);

    }


}

