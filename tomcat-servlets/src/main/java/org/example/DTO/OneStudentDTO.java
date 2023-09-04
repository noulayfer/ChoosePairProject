package org.example.DTO;

import lombok.Data;
import org.example.model.Student;

import java.util.List;
import java.util.Map;

@Data
public class OneStudentDTO {
    private List<Student> firstGroup;
    private List<Student> secondGroup;
    private Map<String, Double> namesAndMarks;
    private List<Student> respondedStudents;
    private List<Student> upsetStudents;
    private Student student;

    public OneStudentDTO(MainPageDTO dto, Student student) {
        namesAndMarks = dto.getNamesAndMarks();
        firstGroup = dto.getFirstGroup();
        secondGroup = dto.getSecondGroup();
        upsetStudents = dto.getUpsetStudents();
        respondedStudents = dto.getRespondedStudents();
        this.student = student;
    }
}
