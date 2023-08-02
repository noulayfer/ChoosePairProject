package org.example.controller;

import org.example.DTO.TwoSubGroupsAndTwoPeopleDTO;
import org.example.model.Point;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name="updateScore", value = "/update-score")
public class AddPoints extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        System.out.println(name);
        LocalDate localDate = LocalDate.now();
        Student studentByName = studentService.getStudentFromList(name);
        LinkedList<Point> points = studentByName.getPoints();
        if (points.peekLast() == null) {
            points.add(new Point(0));
        }
        studentByName.setPoints(points);
        System.out.println(studentByName);

        Point last = points.peekLast();

        double score = last.getScore();
        LocalDate localDate1 = last.getLocalDate();
        if (localDate1 == null || localDate.isEqual(localDate1)) {
            last.setScore(++score);
        } else {
            studentByName.getPoints().add(new Point(1));
        }
        req.setAttribute("score", points.getLast().getScore());
        List<Student> pairOfStudent = studentService.getLastPair();
        List<Student> firstSubGroup = studentService.getFirstSubGroup();
        List<Student> secondSubGroup = studentService.getSecondSubGroup();

        req.setAttribute("firstScore", pairOfStudent.get(0).getPoints().peekLast().getScore());
        req.setAttribute("secondScore", pairOfStudent.get(1).getPoints().peekLast().getScore());
        req.setAttribute("firstStudent", pairOfStudent.get(0));
        req.setAttribute("secondStudent", pairOfStudent.get(1));
        req.setAttribute("firstGroup", firstSubGroup);
        req.setAttribute("secondGroup", secondSubGroup);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
