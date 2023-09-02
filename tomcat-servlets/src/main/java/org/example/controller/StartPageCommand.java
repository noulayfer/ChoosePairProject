package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.StartPageDTO;
import org.example.DTO.TwoSubGroups;
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
import java.util.Map;

public class StartPageCommand implements Command {

    StudentService2 studentService;
    public StartPageCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StartPageDTO dto = ServletUtil.setStartPageDTO(studentService);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
