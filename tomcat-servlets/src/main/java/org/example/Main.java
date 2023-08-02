package org.example;

import org.example.config.JdbcUtil;
import org.example.repository.JdbcPointsRepository;
import org.example.repository.JdbcStudentRepository;
import org.example.service.StudentService;

public class Main {
    public static void main(String[] args) {
        JdbcUtil.getConnection();
        JdbcStudentRepository jdbcStudentRepository = new JdbcStudentRepository();
        JdbcPointsRepository jdbcPointsRepository = new JdbcPointsRepository();
        jdbcStudentRepository.createTableIfNotExists();
        jdbcPointsRepository.createTableIfNotExists();
    }
}