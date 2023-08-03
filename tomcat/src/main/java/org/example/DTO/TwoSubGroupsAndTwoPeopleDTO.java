package org.example.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;

import java.util.List;

@Getter
@Setter
public class TwoSubGroupsAndTwoPeopleDTO {
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    List<Student> pairOfStudent;

    public TwoSubGroupsAndTwoPeopleDTO(List<Student> firstGroup, List<Student> secondGroup, List<Student> pair) {
        this.firstSubGroup = firstGroup;
        this.secondSubGroup = secondGroup;
        this.pairOfStudent = pair;
    }
}
