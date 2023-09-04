package org.example.DTO;

import lombok.Data;
import org.example.model.Student;

import java.util.List;
import java.util.Map;

@Data
public class MainPageDTO {
    private List<Student> firstGroup;
    private List<Student> secondGroup;
    private Map<String, Double> namesAndMarks;
    private List<Student> respondedStudents;
    private List<Student> upsetStudents;
}