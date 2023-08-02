package org.example.repository;

import lombok.SneakyThrows;
import org.example.config.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;

public class JdbcPointsRepository {
    @SneakyThrows
    public void createTableIfNotExists() {
        Connection connection = JdbcUtil.getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS Points (" +
                "    id SERIAL PRIMARY KEY," +
                "    localDate DATE NOT NULL," +
                "    score DOUBLE PRECISION NOT NULL," +
                "    student_id INT," +
                "    FOREIGN KEY (student_id) REFERENCES Student(id)" +
                ");").executeUpdate();
    }

    @SneakyThrows
    public ResultSet getPointsOfUser(int id) {
        Connection connection = JdbcUtil.getConnection();
        ResultSet resultSet = connection.prepareStatement(
                        "SELECT * FROM Points WHERE student_id = ?;")
                        .executeQuery();
        return resultSet;
    }
}