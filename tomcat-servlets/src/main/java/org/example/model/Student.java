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
    private final JdbcStudentRepository jdbcStudentRepository = new JdbcStudentRepository();
    private double mark;

    public Student(String name, int numberOfGroup) {
//        int numberOfRaws = jdbcStudentRepository.getNumberOfRaws();
//        id = ++numberOfRaws;
        this.name = name;
        this.numberOfGroup = numberOfGroup;
    }

    public Student(String name, int numberOfGroup, int id) {
        this.name = name;
        this.numberOfGroup = numberOfGroup;
        this.id = id;
    }

}
