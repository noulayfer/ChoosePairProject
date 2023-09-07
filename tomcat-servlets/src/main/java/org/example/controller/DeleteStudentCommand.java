package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.CommonPageDTO;
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

public class DeleteStudentCommand implements Command {

    StudentService2 studentService;
    public DeleteStudentCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        studentService.deleteByName(name);
        List<Student> lastPair = studentService.getLastPair();
        if (lastPair.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/controller?command=students");
        } else {
            CommonPageDTO dto = ServletUtil.getCommonPageDTO(lastPair, studentService);
            ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
            String json = objectMapper.writeValueAsString(dto);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        }
    }
}
