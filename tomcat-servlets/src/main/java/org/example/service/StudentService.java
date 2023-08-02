package org.example.service;

import lombok.SneakyThrows;
import org.example.DTO.TwoSubGroups;
import org.example.DTO.TwoSubGroupsAndTwoPeopleDTO;
import org.example.model.Point;
import org.example.model.Student;
import org.example.repository.JdbcPointsRepository;
import org.example.repository.JdbcStudentRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StudentService {
    JdbcStudentRepository jdbcStudentRepository;
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    JdbcPointsRepository jdbcPointsRepository;

    public StudentService(JdbcStudentRepository jdbcStudentRepository) {
        this.jdbcStudentRepository = new JdbcStudentRepository();
        jdbcPointsRepository = new JdbcPointsRepository();
        ResultSet studentsByGroup1 = jdbcStudentRepository.getStudentsByGroup(1);
        ResultSet studentsByGroup2 = jdbcStudentRepository.getStudentsByGroup(2);
        firstSubGroup = mapResultSetToStudents(studentsByGroup1);
        secondSubGroup = mapResultSetToStudents(studentsByGroup2);
    }

    public TwoSubGroups getTwoSubGroups() {
        return new TwoSubGroups(firstSubGroup, secondSubGroup);
    }

    public TwoSubGroupsAndTwoPeopleDTO getPairOfStudent() {
        Student student1 = choosePersonFirstGroup();
        Student student2 = choosePersonSecondGroup();
        while (student1.getPreviousOpponent() != student2.getId()) {
            student2 = choosePersonSecondGroup();
        }
        firstSubGroup.remove(student1);
        secondSubGroup.remove(student2);
        List<Student> pair = List.of(student1, student2);
        return new TwoSubGroupsAndTwoPeopleDTO(firstSubGroup, secondSubGroup, pair);
    }

    public Student choosePersonFirstGroup() {
        Random random = new Random();
        int i = random.nextInt(firstSubGroup.size());
        return firstSubGroup.get(i);
    }

    public Student choosePersonSecondGroup() {
        Random random = new Random();
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
//            ResultSet pointsOfUser = jdbcPointsRepository.getPointsOfUser(id);
//            LinkedList<Point> points = mapResultSetToPoints(pointsOfUser);
            Student student = new Student(name, anInt, id);
            student.setPreviousOpponent(previousOpponent);
//            student.setPoints(points);
            students.add(student);
        }
        return students;
    }

    @SneakyThrows
    public LinkedList<Point> mapResultSetToPoints(ResultSet resultSet) {
        LinkedList<Point> points = new LinkedList<>();
        while (resultSet.next()) {
            Date localDate = resultSet.getDate("localDate");
            double score = resultSet.getDouble("score");
            points.add(new Point(localDate.toLocalDate(), score));
        }
        return points;
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

}
