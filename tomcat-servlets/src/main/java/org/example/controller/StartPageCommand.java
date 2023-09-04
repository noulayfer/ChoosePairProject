package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.MainPageDTO;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StartPageCommand implements Command {

    StudentService2 studentService;
    public StartPageCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MainPageDTO dto = ServletUtil.getStartPageDTO(studentService);
        ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
        String json = objectMapper.writeValueAsString(dto);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
