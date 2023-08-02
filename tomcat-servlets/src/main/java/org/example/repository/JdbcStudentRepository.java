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
                        "    id PRIMARY KEY," +
                        "    name VARCHAR(255) NOT NULL," +
                        "    number_of_group INTEGER NOT NULL," +
                        "    previous_opponent INTEGER," +
                        "    id REFERENCES \"Points\"(user_id)" +
                        ");"
        ).executeUpdate();
    }
    @SneakyThrows
    public int getNumberOfRaws() {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "SELECT COUNT(*) FROM \"Students\"";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        ResultSet resultSet = preparedStatement.executeQuery();
        int amountOfRaws = resultSet.getInt(1);
        return amountOfRaws;
    }

    @SneakyThrows
    public ResultSet getStudentsByGroup(int numberOfGroup) {
        Connection connection = JdbcUtil.getConnection();
        String sqlRequest = "SELECT * FROM \"Students\" WHERE numberOfGroup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setInt(1, numberOfGroup);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
