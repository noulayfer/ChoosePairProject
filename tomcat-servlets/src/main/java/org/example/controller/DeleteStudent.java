package org.example.controller;

import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudent extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = new StudentService(new JdbcStudentRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        studentService.deleteByName(name);
        super.doPost(req, resp);
    }
}
