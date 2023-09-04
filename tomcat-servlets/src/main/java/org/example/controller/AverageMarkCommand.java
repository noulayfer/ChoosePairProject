package org.example.controller;

import org.example.DTO.AverageDTO;
import org.example.DTO.MarkDTO;
import org.example.DTO.PageWithMarksAndPairDTO;
import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AverageMarkCommand implements Command {
    StudentService2 studentService;
    public AverageMarkCommand() {
        studentService = StudentService2.getInstance();
    }
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> pairOfStudent = studentService.getLastPair();
        ServletUtil.getCommonPageDTO(pairOfStudent, studentService);
        MarkDTO markDTO = new MarkDTO(0, 0);
        PageWithMarksAndPairDTO dto = null;
        if (!pairOfStudent.isEmpty()) {
            markDTO = ServletUtil.getMarkDTO(ServletUtil.getMark(pairOfStudent.get(0), studentService),
                    ServletUtil.getMark(pairOfStudent.get(1), studentService));
        }
        if (!studentService.isSaved()) {
            dto = ServletUtil.getPageWithMarksAndPair
                    (studentService, markDTO.getMark1(), markDTO.getMark2());

        } else {
            AverageDTO averageDTO = ServletUtil.getAverageDTO(studentService);
            ServletUtil.getFullDTO(dto, averageDTO);

        }
    }
}
