package org.example.repository;

import lombok.SneakyThrows;
import org.example.config.JdbcUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class JdbcStudentRepository {

    @SneakyThrows
    public void createTableIfNotExists() {
        Connection connection = JdbcUtil.getConnection();
        connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Student (" +
                        "    id SERIAL PRIMARY KEY," +
                        "    name VARCHAR(255) NOT NULL," +
                        "    number_of_group INTEGER NOT NULL" +
                        ");"
        ).executeUpdate();
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
    public void updateStudent(int id, double mark, int previousOpponent) {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "UPDATE Student SET mark = ?, previous_opponent = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setDouble(1, mark);
        preparedStatement.setInt(2, previousOpponent);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
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

    @SneakyThrows
    public void insertStudent(String name, int groupId) {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student" +
                "(name, number_of_group) " +
                "VALUES(?, ?);");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, groupId);
        preparedStatement.executeUpdate();
    }
}
