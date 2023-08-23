package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Battle {

    private int id;
    private LocalDate date;
    private int opponent;
    private double mark;

    public Battle(int opponent, double mark) {
        this.opponent = opponent;
        this.mark = mark;
        this.date = LocalDate.now();
    }
}
