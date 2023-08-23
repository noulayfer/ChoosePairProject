package org.example.controller;

import org.example.DTO.TwoSubGroupsAndLastPairAndDeletedUsersDTO;
import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChoosePairCommand implements Command {

    StudentService2 studentService;
    public ChoosePairCommand() {
        studentService = StudentService2.getInstance();
    }


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> lastPair = studentService.getLastPair();
        if (!lastPair.isEmpty()) {
            Student student1 = lastPair.get(0);
            Student student2 = lastPair.get(1);
            double mark1 = ServletUtil.getMark(student1, studentService);
            double mark2 = ServletUtil.getMark(student2, studentService);
            if (mark1 == 0 && mark2 == 0 || !studentService.isFlag()) {
                ServletUtil.setCommonAttributes(req, student1, student2,
                        studentService.getTwoSubGroups(),
                        studentService.getDeletedUsers());
                ServletUtil.setCommonMarkAttributes(req, mark1, mark2);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
                requestDispatcher.forward(req, resp);
                return;
            }
        }

        TwoSubGroupsAndLastPairAndDeletedUsersDTO dto;
        try {
            dto = studentService.getPairOfStudent();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }

        List<Student> pairOfStudent = dto.getPairOfStudent();

        ServletUtil.setCommonAttributes(req, pairOfStudent.get(0), pairOfStudent.get(1),
                studentService.getTwoSubGroups(),
                studentService.getDeletedUsers());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
