package org.example.controller;

import org.example.model.Battle;
import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AddPointsCommand implements Command{
        StudentService2 studentService;
    public AddPointsCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || name.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }

        Student student;
        try {
            student = studentService.getStudentFromLastPair(name);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }
        Optional<Battle> lastBattle = studentService.getLastBattle(student);
        double mark = lastBattle.get().getMark();
        mark += 0.5;
        lastBattle.get().setMark(mark);

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
