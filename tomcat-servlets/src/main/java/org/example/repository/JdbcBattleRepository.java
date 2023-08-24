package org.example.repository;

import lombok.SneakyThrows;
import org.example.config.JdbcUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class JdbcBattleRepository {
    @SneakyThrows
    public void createTableIfNotExists() {
        Connection connection = JdbcUtil.getConnection();
        connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Battles (" +
                        "    id SERIAL PRIMARY KEY," +
                        "    date DATE NOT NULL," +
                        "    opponent INTEGER," +
                        "    mark REAL," +
                        "student_id INTEGER REFERENCES Student(id)" +
                        ");"
        ).executeUpdate();
    }


    @SneakyThrows
    public void insertBattle(LocalDate date, int opponentId, double mark, int studentId) {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Battles" +
                "(date, opponent, mark, student_id) " +
                "VALUES(?, ?, ?, ?);");
        preparedStatement.setDate(1, Date.valueOf(date));
        preparedStatement.setInt(2, opponentId);
        preparedStatement.setDouble(3, mark);
        preparedStatement.setInt(4, studentId);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public ResultSet getBattlesByStudentId(int studentId) {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Battles " +
                "WHERE student_id = ?;");
        preparedStatement.setInt(1, studentId);
        return preparedStatement.executeQuery();
    }

    @SneakyThrows
    public ResultSet getBattlesByDate(LocalDate localDate) {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, date, opponent, mark, student_id " +
                        "FROM Battles WHERE Date = ?");
        preparedStatement.setDate(1, Date.valueOf(localDate));
        return preparedStatement.executeQuery();
    }
}
