package org.example.util;

import org.example.DTO.TwoSubGroups;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.service.StudentService2;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ServletUtil {
    private ServletUtil(){}
    public static void setCommonAttributes(HttpServletRequest req, Student student1, Student student2, TwoSubGroups twoSubGroups, List<Student> deletedStudents) {
        req.setAttribute("firstStudent", student1);
        req.setAttribute("secondStudent", student2);
        req.setAttribute("firstGroup", twoSubGroups.getFirstSubGroup());
        req.setAttribute("secondGroup", twoSubGroups.getSecondSubGroup());
        req.setAttribute("deletedStudents", deletedStudents);
    }

    public static void setCommonMarkAttributes(HttpServletRequest req, double mark1, double mark2) {
        req.setAttribute("firstScore", mark1);
        req.setAttribute("secondScore", mark2);
    }

    public static double getMark(Student student, StudentService2 studentService) {
        Optional<Battle> lastBattle = studentService.getLastBattle(student);
        return lastBattle.get().getMark();
    }
}
