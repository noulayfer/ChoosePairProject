package org.example.controller;

import org.example.DTO.TwoSubGroupsAndTwoPeopleDTO;
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
import java.util.List;

@WebServlet(name = "ChoosePair", value = "/create-pair")
public class ChoosePair extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentService = StudentService.getInstance(new JdbcStudentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TwoSubGroupsAndTwoPeopleDTO dto = studentService.getPairOfStudent();
        List<Student> pairOfStudent = dto.getPairOfStudent();
        List<Student> firstSubGroup = dto.getFirstSubGroup();
        List<Student> secondSubGroup = dto.getSecondSubGroup();

        req.setAttribute("firstStudent", pairOfStudent.get(0));
        req.setAttribute("secondStudent", pairOfStudent.get(1));
        req.setAttribute("firstGroup", firstSubGroup);
        req.setAttribute("secondGroup", secondSubGroup);

//        if (firstSubGroup.isEmpty() || secondSubGroup.isEmpty()) {
//
//            double averageMarkOne = studentService.getAverageMark(1);
//            double averageMarkTwo = studentService.getAverageMark(2);
//
//            req.setAttribute("markOne", averageMarkOne);
//            req.setAttribute("markTwo", averageMarkTwo);
//        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }
}
