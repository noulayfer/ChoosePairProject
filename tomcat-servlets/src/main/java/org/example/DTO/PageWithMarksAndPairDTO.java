package org.example.DTO;

import lombok.Data;
import org.example.model.Student;

import java.util.List;
import java.util.Map;

@Data
public class PageWithMarksAndPairDTO {
    private List<Student> firstGroup;
    private List<Student> secondGroup;
    private Map<String, Double> namesAndMarks;
    private List<Student> respondedStudents;
    private List<Student> upsetStudents;
    private double mark1;
    private double mark2;
    private Student student1;
    private Student student2;


    public PageWithMarksAndPairDTO(CommonPageDTO dto, MarkDTO markDTO) {
        namesAndMarks = dto.getNamesAndMarks();
        firstGroup = dto.getFirstGroup();
        secondGroup = dto.getSecondGroup();
        upsetStudents = dto.getUpsetStudents();
        respondedStudents = dto.getRespondedStudents();
        student1 = dto.getStudent1();
        student2 = dto.getStudent2();
        mark1 = markDTO.getMark1();
        mark2 = markDTO.getMark2();
    }
}
