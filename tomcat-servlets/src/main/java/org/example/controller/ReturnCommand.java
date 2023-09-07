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

public class ReturnCommand implements Command {

    StudentService2 studentService;

    public ReturnCommand() {
        studentService = StudentService2.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        List<Student> pairOfStudent = studentService.getLastPair();
        restoreStudent(name);
        if (pairOfStudent.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/controller?command=students");
            return;
        }
        MarkDTO markDTO = ServletUtil.getMarkDTO(ServletUtil.getMark(pairOfStudent.get(0), studentService),
                ServletUtil.getMark(pairOfStudent.get(1), studentService));
        PageWithMarksAndPairDTO dto = ServletUtil.
                getPageWithMarksAndPair(studentService, markDTO.getMark1(), markDTO.getMark2());
        ObjectMapper objectMapper = ServletUtil.getObjectMapperWithTimeModule();
        String json = objectMapper.writeValueAsString(dto);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private List<Student> getGroupByNumber(int groupNumber) {
        return groupNumber == 1 ? studentService.getFirstSubGroup() :
                studentService.getSecondSubGroup();
    }

    private void restoreStudent(String name) {
        Student studentFromDeleted = studentService.getStudentFromUpset(name);
        int numberOfGroup = studentFromDeleted.getNumberOfGroup();
        List<Student> groupByNumber = getGroupByNumber(numberOfGroup);
        groupByNumber.add(studentFromDeleted);
        List<Student> upsetStudents = studentService.getUpsetStudents();
        upsetStudents.remove(studentFromDeleted);
    }
}
