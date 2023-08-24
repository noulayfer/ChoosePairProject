package org.example.service;

import lombok.SneakyThrows;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.repository.JdbcBattleRepository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BattleService {

    private static BattleService BATTLE_SERVICE;
    private JdbcBattleRepository jdbcBattleRepository;

    private BattleService() {
        jdbcBattleRepository = new JdbcBattleRepository();
    }

    public static BattleService getInstance() {
        if (BATTLE_SERVICE == null) {
            BATTLE_SERVICE = new BattleService();
        }
        return BATTLE_SERVICE;
    }

    public List<Battle> getBattlesById(int studentId) {
        ResultSet battlesByStudentId = jdbcBattleRepository.getBattlesByStudentId(studentId);
        return mapResultSetToBattle(battlesByStudentId);
    }
    public void saveStudentBattle(Battle battle, int studentId) {
        jdbcBattleRepository.insertBattle(battle.getDate(), battle.getOpponent(),
                battle.getMark(), studentId);
    }

    public List<Battle> getBattlesByDate(LocalDate localDate) {
        ResultSet battlesByDate = jdbcBattleRepository.getBattlesByDate(localDate);
        return mapResultSetToBattle(battlesByDate);
    }
    @SneakyThrows
    private List<Battle> mapResultSetToBattle(ResultSet resultSet) {
        List<Battle> battles = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            int opponentId = resultSet.getInt("opponent");
            double mark = resultSet.getDouble("mark");
            Battle battle = new Battle(opponentId, mark);
            battle.setDate(date);
            battle.setId(id);
            battles.add(battle);
        }
        return battles;
    }
}
