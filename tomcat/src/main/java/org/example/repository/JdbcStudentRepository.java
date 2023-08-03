package org.example.repository;

import lombok.SneakyThrows;
import org.example.config.JdbcUtil;
import org.example.model.Student;

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
                        "    previous_opponent INTEGER," +
                        "    mark DOUBLE" +
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
    public ResultSet getStudentByName(String name) {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT * FROM Student WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    @SneakyThrows
    public void updateStudent(int id, double mark, int previousOpponent) {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "UPDATE Student SET mark=?, previous_opponent=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setDouble(1, mark);
        preparedStatement.setInt(2, previousOpponent);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public Student getStudentById(int id) {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "SELECT * FROM Student WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setNumberOfGroup(resultSet.getInt("number_of_group"));
        student.setPreviousOpponent(resultSet.getInt("previous_opponent"));
        student.setMark(resultSet.getDouble("mark"));
        return student;
    }


}
