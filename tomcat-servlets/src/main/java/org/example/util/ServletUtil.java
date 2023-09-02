package org.example.util;

import org.example.DTO.StartPageDTO;
import org.example.DTO.TwoSubGroups;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.service.StudentService2;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ServletUtil {
    private ServletUtil(){}
    public static void setCommonAttributes(HttpServletRequest req, List<Student> pair, StudentService2 service) {
        setStartPageAttributes(req, service);
        if (pair.isEmpty()) return;
        req.setAttribute("firstStudent", pair.get(0));
        req.setAttribute("secondStudent", pair.get(1));
    }

    public static void setCommonMarkAttributes(HttpServletRequest req, double mark1, double mark2) {
        req.setAttribute("firstScore", mark1);
        req.setAttribute("secondScore", mark2);
    }

    public static double getMark(Student student, StudentService2 studentService) {
        Optional<Battle> lastBattle = studentService.getLastBattle(student);
        return lastBattle.map(Battle::getMark).orElse(0.0);
    }


    public static Map<String, Double>  mapToDoubleNames(StudentService2 studentService2) {
        Map<String, Double> doubleNames = new LinkedHashMap<>();
        List<Student> respondedUsers = studentService2.getRespondedUsers();
        respondedUsers.sort((user1, user2) -> {
            double mark1 = getMark(user1, studentService2);
            double mark2 = getMark(user2, studentService2);
            return (int) (mark2 * 10 - mark1 * 10);
        });
        for(Student student : respondedUsers) {
            double mark = getMark(student, studentService2);
            doubleNames.put(student.getName(), mark);
        }
        return doubleNames;
    }

    public static void updateMark(String name, StudentService2 service, double increment) {
        Student student = service.getStudentFromAnywhere(name);
        Optional<Battle> lastBattle = service.getLastBattle(student);
        double mark = lastBattle.get().getMark();
        mark += increment;
        lastBattle.get().setMark(mark);
    }

    public static void setAverage(HttpServletRequest req, StudentService2 service) {
        req.setAttribute("markOne", service.getAverageMark(1));
        req.setAttribute("markTwo", service.getAverageMark(2));
    }

    public static void setStartPageAttributes(HttpServletRequest req, StudentService2 service) {
        req.setAttribute("firstGroup", service.getFirstSubGroup());
        req.setAttribute("secondGroup", service.getSecondSubGroup());
        Map<String, Double> stringDoubleMap = ServletUtil.mapToDoubleNames(service);
        req.setAttribute("namesAndMarks", stringDoubleMap);
        req.setAttribute("respondedStudents", service.getRespondedUsers());
        List<Student> upsetStudents = service.getUpsetStudents();
        req.setAttribute("upsetStudents", upsetStudents);
    }

    public static StartPageDTO setStartPageDTO(StudentService2 service) {
        StartPageDTO dto = new StartPageDTO();
        dto.setFirstGroup(service.getFirstSubGroup());
        dto.setSecondGroup(service.getSecondSubGroup());
        dto.setNamesAndMarks(ServletUtil.mapToDoubleNames(service));
        dto.setRespondedStudents(service.getRespondedUsers());
        dto.setUpsetStudents(service.getUpsetStudents());
        return dto;
    }

}
