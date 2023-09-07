package org.example;

import org.example.config.JdbcUtil;
import org.example.repository.JdbcBattleRepository;
import org.example.repository.JdbcStudentRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        JdbcStudentRepository jdbcStudentRepository = new JdbcStudentRepository();
        JdbcBattleRepository jdbcBattleRepository = new JdbcBattleRepository();
        jdbcStudentRepository.createTableIfNotExists();
        jdbcBattleRepository.createTableIfNotExists();
        jdbcStudentRepository.insertStudent("Daniil", 2);
        jdbcStudentRepository.insertStudent("Alexander", 2);
        jdbcStudentRepository.insertStudent("Illya", 2);
        jdbcStudentRepository.insertStudent("Alexander M", 1);
        jdbcStudentRepository.insertStudent("Yousuf", 1);
        jdbcStudentRepository.insertStudent("Stephan", 1);

    }
}