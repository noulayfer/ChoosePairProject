package org.example.DTO;

import lombok.Data;
import org.example.model.Student;

import java.util.List;
import java.util.Map;

@Data
public class CommonPageDTO {
    private List<Student> firstGroup;
    private List<Student> secondGroup;
    private Map<String, Double> namesAndMarks;
    private List<Student> respondedStudents;
    private List<Student> upsetStudents;
    private Student student1;
    private Student student2;

    public CommonPageDTO(MainPageDTO dto, List<Student> pair) {
        firstGroup = dto.getFirstGroup();
        secondGroup = dto.getSecondGroup();
        namesAndMarks = dto.getNamesAndMarks();
        respondedStudents = dto.getRespondedStudents();
        upsetStudents = dto.getUpsetStudents();
        if (pair.isEmpty()) return;
        student1 = pair.get(0);
        student2 = pair.get(1);
    }
}
