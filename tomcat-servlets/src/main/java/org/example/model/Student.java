package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.repository.JdbcStudentRepository;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Student {
    private int id;
    private String name;
    private int numberOfGroup;
    private List<Battle> battles;

    public Student(String name, int numberOfGroup) {
        this.name = name;
        this.numberOfGroup = numberOfGroup;
    }

    public Student(String name, int numberOfGroup, int id) {
        this.name = name;
        this.numberOfGroup = numberOfGroup;
        this.id = id;
    }

}
