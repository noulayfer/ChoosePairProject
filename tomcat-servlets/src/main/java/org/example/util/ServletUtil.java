package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.DTO.*;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.service.StudentService2;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ServletUtil {
    private ServletUtil(){}
    public static CommonPageDTO getCommonPageDTO(List<Student> pair, StudentService2 service) {
        return new CommonPageDTO(getStartPageDTO(service), pair);
    }

    public static MarkDTO getMarkDTO(double mark1, double mark2) {
        return new MarkDTO(mark1, mark2);
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

    public static AverageDTO getAverageDTO(StudentService2 service) {
        double averageMark1 = service.getAverageMark(1);
        double averageMark2 = service.getAverageMark(2);
        return new AverageDTO(averageMark1, averageMark2);
    }

    public static MainPageDTO getStartPageDTO(StudentService2 service) {
        MainPageDTO dto = new MainPageDTO();
        dto.setFirstGroup(service.getFirstSubGroup());
        dto.setSecondGroup(service.getSecondSubGroup());
        dto.setNamesAndMarks(ServletUtil.mapToDoubleNames(service));
        dto.setRespondedStudents(service.getRespondedUsers());
        dto.setUpsetStudents(service.getUpsetStudents());
        return dto;
    }

    public static PageWithMarksAndPairDTO getPageWithMarksAndPair(StudentService2 service,
                                                                  double mark1, double mark2) {
        return new PageWithMarksAndPairDTO(
                getCommonPageDTO(service.getLastPair(), service), getMarkDTO(mark1, mark2));
    }

    public static FullDTO getFullDTO(PageWithMarksAndPairDTO dto, AverageDTO averageDTO) {
        return new FullDTO(dto, averageDTO);
    }

    public static ObjectMapper getObjectMapperWithTimeModule() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
