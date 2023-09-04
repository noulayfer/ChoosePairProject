package org.example.controller;

import org.example.model.Student;
import org.example.service.StudentService2;
import org.example.util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
//        ServletUtil.setCommonAttributes(request, pairOfStudent, studentService);
//        ServletUtil.getMarkDTO(request, ServletUtil.getMark(pairOfStudent.get(0), studentService),
//                ServletUtil.getMark(pairOfStudent.get(1), studentService));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome-page.jsp");
        requestDispatcher.forward(request, response);
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
