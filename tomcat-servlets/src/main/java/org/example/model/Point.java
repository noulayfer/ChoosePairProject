package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Point {
    private LocalDate localDate;
    private double score;


    public Point(double score) {
        localDate = getLocalDate();
        this.score = score;
    }

    public Point(LocalDate localDate, double score) {
        this.localDate = localDate;
        this.score = score;
    }

}
