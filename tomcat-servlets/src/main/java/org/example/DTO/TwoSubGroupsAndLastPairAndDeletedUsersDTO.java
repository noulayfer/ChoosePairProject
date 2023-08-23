package org.example.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;

import java.util.List;

@Getter
@Setter
public class TwoSubGroupsAndLastPairAndDeletedUsersDTO {
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    List<Student> pairOfStudent;
    List<Student> deletedStudents;

    public TwoSubGroupsAndLastPairAndDeletedUsersDTO(List<Student> firstGroup, List<Student> secondGroup,
                                                     List<Student> pair, List<Student> deletedStudents) {
        this.firstSubGroup = firstGroup;
        this.secondSubGroup = secondGroup;
        this.pairOfStudent = pair;
        this.deletedStudents = deletedStudents;
    }
}
