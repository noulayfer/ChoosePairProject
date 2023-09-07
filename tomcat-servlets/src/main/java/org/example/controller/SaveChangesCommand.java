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
import java.util.Map;

public class SaveChangesCommand implements Command {

    StudentService2 studentService;

    public SaveChangesCommand() {
        studentService = StudentService2.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> lastPair = studentService.getLastPair();
        if (lastPair.isEmpty()) {
            request.getRequestDispatcher("/controller?command=students")
                    .forward(request, response);
            return;
        }
        studentService.saveBattlesLastPair();
        CommonPageDTO dto = ServletUtil.getCommonPageDTO(lastPair, studentService);
        ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }


}

