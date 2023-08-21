package org.example.controller;

import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudentCommand implements Command {

    StudentService studentService;
    public DeleteStudentCommand() {
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        studentService.deleteByName(name);
        resp.sendRedirect(req.getContextPath() + "/controller?command=students");;
    }
}
