package org.example.repository;

import lombok.SneakyThrows;
import org.example.config.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcStudentRepository {

    @SneakyThrows
    public void createTableIfNotExists() {
        Connection connection = JdbcUtil.getConnection();
        connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Student (" +
                        "    id INT PRIMARY KEY," +
                        "    name VARCHAR(255) NOT NULL," +
                        "    number_of_group INTEGER NOT NULL," +
                        "    previous_opponent INTEGER" +
                        ");"
        ).executeUpdate();
    }
    @SneakyThrows
    public int getNumberOfRaws() {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "SELECT COUNT(*) FROM Student";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        ResultSet resultSet = preparedStatement.executeQuery();
        int amountOfRaws = resultSet.getInt(1);
        return amountOfRaws;
    }

    @SneakyThrows
    public ResultSet getStudentsByGroup(int numberOfGroup) {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "SELECT * FROM Student WHERE number_of_group = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setInt(1, numberOfGroup);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    @SneakyThrows
    public ResultSet getStudentByName(String name) {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT * FROM Student WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
