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
import java.util.Map;

public class ChoosePairCommand implements Command {

    StudentService2 studentService;

    public ChoosePairCommand() {
        studentService = StudentService2.getInstance();
    }


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> lastPair = studentService.getLastPair();
        if (!lastPair.isEmpty()) {
            if (!studentService.isSaved()) {
                ServletUtil.setCommonAttributes(req, lastPair, studentService);
                ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(lastPair.get(0), studentService),
                        ServletUtil.getMark(lastPair.get(1), studentService));
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
                requestDispatcher.forward(req, resp);
                return;
            }
        }
        studentService.updateLastPair();
        List<Student> updatedPair = studentService.getLastPair();
        if (updatedPair.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }

        if (!(updatedPair.get(0) == null) ^ !(updatedPair.get(1) == null)) {
            setAttributesForOneStudent(req);
            req.getRequestDispatcher("welcome-page.jsp").forward(req, resp);
            return;
        }
        ServletUtil.setCommonAttributes(req, updatedPair, studentService);
        ServletUtil.setCommonMarkAttributes(req, ServletUtil.getMark(updatedPair.get(0), studentService),
                ServletUtil.getMark(updatedPair.get(1), studentService));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void setAttributesForOneStudent(HttpServletRequest req) {
        List<Student> lastPair = studentService.getLastPair();
            ServletUtil.setStartPageAttributes(req, studentService);
        if (lastPair.get(0) == null) {
            Student student = lastPair.get(1);
            req.setAttribute("secondStudent", student);
        } else {
            Student student = lastPair.get(0);
            req.setAttribute("firstStudent", student);
        }
    }
}
