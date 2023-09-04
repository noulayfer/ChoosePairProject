package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.MainPageDTO;
import org.example.DTO.MarkDTO;
import org.example.DTO.OneStudentDTO;
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
                MarkDTO markDTO = ServletUtil.getMarkDTO(ServletUtil.getMark(lastPair.get(0), studentService),
                        ServletUtil.getMark(lastPair.get(1), studentService));

                PageWithMarksAndPairDTO dto = ServletUtil.getPageWithMarksAndPair
                        (studentService, markDTO.getMark1(), markDTO.getMark2());
                ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
                String json = objectMapper.writeValueAsString(dto);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print(json);
                out.flush();

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
            OneStudentDTO dto = getOneStudentDTO();
            ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
            String json = objectMapper.writeValueAsString(dto);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
            return;
        }
        MarkDTO markDTO = ServletUtil.getMarkDTO(ServletUtil.getMark(lastPair.get(0), studentService),
                ServletUtil.getMark(lastPair.get(1), studentService));

        PageWithMarksAndPairDTO dto = ServletUtil.getPageWithMarksAndPair
                (studentService, markDTO.getMark1(), markDTO.getMark2());
        ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
        String json = objectMapper.writeValueAsString(dto);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private OneStudentDTO getOneStudentDTO() {
        List<Student> lastPair = studentService.getLastPair();
        MainPageDTO startPageDTO = ServletUtil.getStartPageDTO(studentService);
        if (lastPair.get(0) == null) {
            return new OneStudentDTO(startPageDTO, lastPair.get(1));
        } else {
            return new OneStudentDTO(startPageDTO, lastPair.get(1));
        }
    }
}
