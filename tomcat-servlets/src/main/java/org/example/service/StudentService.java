package org.example.service;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.DTO.TwoSubGroups;
import org.example.DTO.TwoSubGroupsAndTwoPeopleDTO;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Getter
public class StudentService {
    JdbcStudentRepository jdbcStudentRepository;
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    List<Student> lastPair;
    private static StudentService STUDENT_SERVICE;


    int groupOneCounter = 0;
    int groupTwoCounter = 0;
    int studentsFromFirstGroup = 0;
    int studentsFromSecondGroup = 0;

    public static StudentService getInstance(JdbcStudentRepository jdbcStudentRepository) {
        if (STUDENT_SERVICE == null) {
            STUDENT_SERVICE = new StudentService(jdbcStudentRepository);
            return STUDENT_SERVICE;
        } else {
            return STUDENT_SERVICE;
        }
    }


    private StudentService(JdbcStudentRepository jdbcStudentRepository) {
        this.jdbcStudentRepository = new JdbcStudentRepository();
        ResultSet studentsByGroup1 = jdbcStudentRepository.getStudentsByGroup(1);
        ResultSet studentsByGroup2 = jdbcStudentRepository.getStudentsByGroup(2);
        firstSubGroup = mapResultSetToStudents(studentsByGroup1);
        secondSubGroup = mapResultSetToStudents(studentsByGroup2);
        lastPair = new ArrayList<>();
    }

    public TwoSubGroups getTwoSubGroups() {
        return new TwoSubGroups(firstSubGroup, secondSubGroup);
    }

    public TwoSubGroupsAndTwoPeopleDTO getPairOfStudent() {
        if (!lastPair.isEmpty()) {
            groupOneCounter += lastPair.get(0).getMark();
            groupTwoCounter += lastPair.get(1).getMark();
        }
        Student student1 = choosePersonFirstGroup();
        Student student2 = choosePersonSecondGroup();

        while (student1.getPreviousOpponent() == student2.getId()) {
            student2 = choosePersonSecondGroup();
       }


        firstSubGroup.remove(student1);
        studentsFromFirstGroup += 1;
        secondSubGroup.remove(student2);
        studentsFromSecondGroup += 1;

        lastPair = new LinkedList<>();
        lastPair.add(student1);
        lastPair.add(student2);
        return new TwoSubGroupsAndTwoPeopleDTO(firstSubGroup, secondSubGroup, lastPair);
    }

    public Student choosePersonFirstGroup() {
        Random random = new Random();
        if (firstSubGroup.size() == 0) {
            throw new IllegalStateException("first group is empty");
        }
        int i = random.nextInt(firstSubGroup.size());
        return firstSubGroup.get(i);
    }

    public Student choosePersonSecondGroup() {
        Random random = new Random();
        if (secondSubGroup.size() == 0) {
            throw new IllegalStateException("second group is empty");
        }
        int i = random.nextInt(secondSubGroup.size());
        return secondSubGroup.get(i);
    }

    @SneakyThrows
    public List<Student> mapResultSetToStudents(ResultSet resultSet) {
        List<Student> students = new ArrayList<>();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int anInt = resultSet.getInt("number_of_group");
            int previousOpponent = resultSet.getInt("previous_opponent");
            double mark = resultSet.getDouble("mark");
            Student student = new Student(name, anInt, id);
            student.setMark(mark);
            student.setPreviousOpponent(previousOpponent);
            students.add(student);
        }
        return students;
    }

    public void deleteByName(String name) {
        firstSubGroup.removeIf(x -> x.getName().equals(name));
        secondSubGroup.removeIf(x -> x.getName().equals(name));
    }

    public Student getStudentByName(String name) {
        ResultSet studentByName = jdbcStudentRepository.getStudentByName(name);
        List<Student> students = mapResultSetToStudents(studentByName);
        return students.get(0);
    }

    public Student getStudentFromList(String name) {
        Student student = lastPair.stream().filter(x -> x.getName().equals(name)).findFirst().get();
        return student;
    }

    //TODO not working while do not create 5 pairs
    public double getAverageMark(int groupNumber) {
        return groupNumber == 1 ? (groupOneCounter + lastPair.get(0).getMark()) / studentsFromFirstGroup :
                (groupTwoCounter + lastPair.get(1).getMark()) / studentsFromSecondGroup;
    }

    public TwoSubGroups showFullStat() {
        ResultSet studentsByGroup1 = jdbcStudentRepository.getStudentsByGroup(1);
        ResultSet studentsByGroup2 = jdbcStudentRepository.getStudentsByGroup(2);
        List<Student> students1 = mapResultSetToStudents(studentsByGroup1);
        List<Student> students2 = mapResultSetToStudents(studentsByGroup2);
        return new TwoSubGroups(students1, students2);
    }
}
