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

public class AverageMarkCommand implements Command {
    StudentService2 studentService;
    public AverageMarkCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double averageMark1 = studentService.getAverageMark(1);
        double averageMark2 = studentService.getAverageMark(2);
        req.setAttribute("markOne", averageMark1);
        req.setAttribute("markTwo", averageMark2);
        Student student1;
        Student student2;
        List<Student> pairOfStudent = studentService.getLastPair();
        if (!pairOfStudent.isEmpty()) {
            student1 = pairOfStudent.get(0);
            student2 = pairOfStudent.get(1);
        } else {
            TwoSubGroups twoSubGroups = studentService.getTwoSubGroups();
            req.setAttribute("firstGroup", twoSubGroups.getFirstSubGroup());
            req.setAttribute("secondGroup", twoSubGroups.getSecondSubGroup());
            req.setAttribute("deletedStudents", studentService.getDeletedUsers());
            req.getRequestDispatcher("welcome-page.jsp").forward(req, resp);
            return;
        }

//        TwoSubGroups twoSubGroups = studentService.getTwoSubGroups();
//        if (twoSubGroups.getFirstSubGroup().isEmpty() && twoSubGroups.getSecondSubGroup().isEmpty()) {
//            req.getRequestDispatcher("welcome-page.jsp").forward(req, resp);;
//            return;
//        }

        ServletUtil.setCommonAttributes(req, student1, student2,
                studentService.getTwoSubGroups(), studentService.getDeletedUsers());
        ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(student1, studentService),
                ServletUtil.getMark(student2, studentService));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
