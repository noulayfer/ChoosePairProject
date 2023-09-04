package org.example.DTO;

import lombok.Data;
import org.example.model.Student;

import java.util.List;
import java.util.Map;

@Data
public class FullDTO {
    private List<Student> firstGroup;
    private List<Student> secondGroup;
    private Map<String, Double> namesAndMarks;
    private List<Student> respondedStudents;
    private List<Student> upsetStudents;
    private double mark1;
    private double mark2;
    private Student student1;
    private Student student2;
    private double averageMark1;
    private double averageMark2;

    public FullDTO(PageWithMarksAndPairDTO dto, AverageDTO averageDTO) {
        namesAndMarks = dto.getNamesAndMarks();
        firstGroup = dto.getFirstGroup();
        secondGroup = dto.getSecondGroup();
        upsetStudents = dto.getUpsetStudents();
        respondedStudents = dto.getRespondedStudents();
        student1 = dto.getStudent1();
        student2 = dto.getStudent2();
        mark1 = dto.getMark1();
        mark2 = dto.getMark2();
        averageMark1 = averageDTO.getAverageMark1();
        averageMark2 = averageDTO.getAverageMark2();
    }
}
