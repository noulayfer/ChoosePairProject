package org.example.service;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.DTO.TwoSubGroups;
import org.example.DTO.TwoSubGroupsAndLastPairAndDeletedUsersDTO;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class StudentService2 {
    private static StudentService2 STUDENT_SERVICE;
    JdbcStudentRepository jdbcStudentRepository;
    BattleService battleService;
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    List<Student> deletedUsers;
    boolean flag = true;

    List<Student> lastPair;
    double groupOneCounter = 0;
    double groupTwoCounter = 0;

    int studentsFromFirstGroup = 0;
    int studentsFromSecondGroup = 0;

    private StudentService2() {
        this.jdbcStudentRepository = new JdbcStudentRepository();
        this.battleService = BattleService.getInstance();
        initializeSubGroups();
        lastPair = new ArrayList<>();
        deletedUsers = new ArrayList<>();
    }

    public static StudentService2 getInstance() {
        if (STUDENT_SERVICE == null) {
            STUDENT_SERVICE = new StudentService2();
        }
        return STUDENT_SERVICE;
    }


    public TwoSubGroupsAndLastPairAndDeletedUsersDTO getPairOfStudent() {
        Student student1;
        try {
            student1 = choosePersonFirstGroup();
        } catch (Exception e) {
            saveBattlesLastPair();
            throw e;
        }
        AtomicReference<Student> student2 = new AtomicReference<>(choosePersonSecondGroup());
        getLastBattle(student1).ifPresent(battle -> {
            while (battle.getOpponent() == student2.get().getId()) {
                student2.set(choosePersonSecondGroup());
                if (secondSubGroup.size() == 1) {
                    break;
                }
            }
        });


        List<Battle> battles1 = student1.getBattles();
        Battle battle1 = new Battle(student2.get().getId(), 0);
        battles1.add(battle1);

        List<Battle> battles2 = student2.get().getBattles();
        Battle battle2 = new Battle(student1.getId(), 0);
        battles2.add(battle2);


        updateSubGroups(student1, student2.get());
        updateLastPair(student1, student2.get());
        flag = false;
        return new TwoSubGroupsAndLastPairAndDeletedUsersDTO(
                new ArrayList<>(firstSubGroup), new ArrayList<>(secondSubGroup),
                new ArrayList<>(lastPair), new ArrayList<>(deletedUsers)
        );
    }

    private void updatePairStatistics(Student student1, Student student2) {
        groupOneCounter += getLastBattle(student1).get().getMark();
        groupTwoCounter += getLastBattle(student2).get().getMark();
    }

    public void saveBattlesLastPair() {
        if (lastPair.size() == 0) {
            return;
        }
        Student student1 = lastPair.get(0);
        Student student2 = lastPair.get(1);
        battleService.saveStudentBattle(getLastBattle(student1).get(), student1.getId());
        battleService.saveStudentBattle(getLastBattle(student2).get(), student2.getId());
        if (!lastPair.isEmpty()) {
            updatePairStatistics(lastPair.get(0), lastPair.get(1));
            flag = true;
            lastPair.clear();
        }
    }

    private void updateSubGroups(Student student1, Student student2) {
        firstSubGroup.remove(student1);
        studentsFromFirstGroup++;
        secondSubGroup.remove(student2);
        studentsFromSecondGroup++;
    }

    private void updateLastPair(Student student1, Student student2) {
        if (!lastPair.isEmpty()) {
            deletedUsers.add(lastPair.get(0));
            deletedUsers.add(lastPair.get(1));
            lastPair.clear();
        }
        lastPair.add(student1);
        lastPair.add(student2);
    }

    public Optional<Battle> getLastBattle(Student student) {
        List<Battle> battles = student.getBattles();
        if (battles.size() == 0) return Optional.empty();
        return Optional.ofNullable(battles.get(battles.size() - 1));
    }

    public Student choosePersonFirstGroup() {
        Random random = new Random();
        if (firstSubGroup.size() == 0) {
            throw new IllegalStateException("first group is empty");
        }
        int i = random.nextInt(firstSubGroup.size());
        return firstSubGroup.get(i);
    }

    public Student choosePersonSecondGroup() {
        Random random = new Random();
        if (secondSubGroup.size() == 0) {
            throw new IllegalStateException("second group is empty");
        }
        int i = random.nextInt(secondSubGroup.size());
        return secondSubGroup.get(i);
    }

    public Student getStudentFromLastPair(String name) {
        return lastPair.stream().filter(x -> x.getName().equals(name)).findFirst().orElseThrow(
                IllegalArgumentException::new);
    }


    public TwoSubGroups getTwoSubGroups() {
        return new TwoSubGroups(new ArrayList<>(firstSubGroup), new ArrayList<>(secondSubGroup));
    }

    private void initializeSubGroups() {
        firstSubGroup = mapResultSetToStudents(jdbcStudentRepository.getStudentsByGroup(1));
        secondSubGroup = mapResultSetToStudents(jdbcStudentRepository.getStudentsByGroup(2));
    }

    @SneakyThrows
    public List<Student> mapResultSetToStudents(ResultSet resultSet) {
        List<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int numberOfGroup = resultSet.getInt("number_of_group");
            Student student = new Student(name, numberOfGroup, id);
            List<Battle> battlesById = battleService.getBattlesById(id);
            student.setBattles(battlesById);
            students.add(student);
        }
        return students;
    }

    public void deleteByName(String name) {
        Student studentByName = getStudentByName(name);
        deletedUsers.add(studentByName);
        firstSubGroup.removeIf(x -> x.getName().equals(name));
        secondSubGroup.removeIf(x -> x.getName().equals(name));
    }

        public Student getStudentByName(String name) {
        ResultSet studentByName = jdbcStudentRepository.getStudentByName(name);
        List<Student> students = mapResultSetToStudents(studentByName);
        return students.get(0);
    }

    public double getAverageMark(int groupNumber) {
        if (lastPair.size() == 0 && groupTwoCounter == 0) {
            return 0.0;
        }
        if (groupNumber == 1) {
            return groupOneCounter / studentsFromFirstGroup;
        } else {
            return groupTwoCounter / studentsFromSecondGroup;
        }
    }

        public TwoSubGroups showFullStat() {
        ResultSet studentsByGroup1 = jdbcStudentRepository.getStudentsByGroup(1);
        ResultSet studentsByGroup2 = jdbcStudentRepository.getStudentsByGroup(2);
        List<Student> students1 = mapResultSetToStudents(studentsByGroup1);
        List<Student> students2 = mapResultSetToStudents(studentsByGroup2);
        return new TwoSubGroups(students1, students2);
    }

}
