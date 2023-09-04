package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.MarkDTO;
import org.example.DTO.PageWithMarksAndPairDTO;
import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        ServletUtil.updateMark(name, studentService, 0.5);
        List<Student> pairOfStudent = studentService.getLastPair();
        if (pairOfStudent.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
            return;
        }
        MarkDTO markDTO = ServletUtil.getMarkDTO(ServletUtil.getMark(pairOfStudent.get(0), studentService),
                ServletUtil.getMark(pairOfStudent.get(1), studentService));

        PageWithMarksAndPairDTO dto = ServletUtil
                .getPageWithMarksAndPair(studentService, markDTO.getMark1(), markDTO.getMark2());
        ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
        String json = objectMapper.writeValueAsString(dto);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
  }
}
