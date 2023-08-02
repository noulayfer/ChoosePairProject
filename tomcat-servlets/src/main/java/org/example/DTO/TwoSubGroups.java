package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;

import java.util.List;

@Data
@AllArgsConstructor
public class TwoSubGroups {
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
}
