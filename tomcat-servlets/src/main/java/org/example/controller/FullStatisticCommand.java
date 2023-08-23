package org.example.controller;

import org.example.DTO.TwoSubGroups;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;
import org.example.service.StudentService2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FullStatisticCommand implements Command {

    StudentService2 studentService;
    public FullStatisticCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TwoSubGroups twoSubGroups = studentService.showFullStat();
        List<Student> firstSubGroup = twoSubGroups.getFirstSubGroup();
        List<Student> secondSubGroup = twoSubGroups.getSecondSubGroup();
        req.setAttribute("secondGroup", secondSubGroup);
        req.setAttribute("firstGroup", firstSubGroup);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("statistic.jsp");
        requestDispatcher.forward(req, resp);
    }
}
