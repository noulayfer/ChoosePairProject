package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.repository.JdbcStudentRepository;

import java.awt.*;
import java.util.LinkedList;

@Getter
@Setter
public class Student {
    private int id;
    private String name;
    private int numberOfGroup;
    private int previousOpponent;
    private LinkedList<Point> points = new LinkedList<>();
    private final JdbcStudentRepository jdbcStudentRepository = new JdbcStudentRepository();

    public Student(String name, int numberOfGroup) {
        int numberOfRaws = jdbcStudentRepository.getNumberOfRaws();
        id = ++numberOfRaws;
        this.name = name;
        this.numberOfGroup = numberOfGroup;
    }

    public void addPoint(Point point) {
        points.push(point);
    }

    public Point getLastPoint() {
        return points.peek();
    }
}
