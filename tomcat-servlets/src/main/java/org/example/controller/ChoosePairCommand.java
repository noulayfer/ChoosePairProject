package org.example.controller;

import org.example.DTO.TwoSubGroupsAndTwoPeopleDTO;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChoosePairCommand implements Command {

    StudentService studentService;
    public ChoosePairCommand() {
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TwoSubGroupsAndTwoPeopleDTO dto;
        try {
            dto = studentService.getPairOfStudent();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }
        List<Student> pairOfStudent = dto.getPairOfStudent();
        List<Student> firstSubGroup = dto.getFirstSubGroup();
        List<Student> secondSubGroup = dto.getSecondSubGroup();

        req.setAttribute("firstStudent", pairOfStudent.get(0));
        req.setAttribute("secondStudent", pairOfStudent.get(1));
        req.setAttribute("firstGroup", firstSubGroup);
        req.setAttribute("secondGroup", secondSubGroup);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
