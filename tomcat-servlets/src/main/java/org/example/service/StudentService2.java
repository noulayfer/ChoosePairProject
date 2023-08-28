package org.example.service;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.DTO.TwoSubGroups;
import org.example.DTO.TwoSubGroupsAndLastPairAndDeletedUsersDTO;
import org.example.model.Battle;
import org.example.model.Student;
import org.example.repository.JdbcStudentRepository;
import org.example.util.ServletUtil;

import java.security.spec.ECField;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Getter
public class StudentService2 {
    private static StudentService2 STUDENT_SERVICE;
    JdbcStudentRepository jdbcStudentRepository;
    BattleService battleService;
    List<Student> firstSubGroup;
    List<Student> secondSubGroup;
    List<Student> respondedUsers;
    List<Student> upsetStudents;
    boolean saved = true;

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
        respondedUsers = new ArrayList<>();
        upsetStudents = new ArrayList<>();
    }

    public static StudentService2 getInstance() {
        if (STUDENT_SERVICE == null) {
            STUDENT_SERVICE = new StudentService2();
        }
        return STUDENT_SERVICE;
    }


    @SneakyThrows
    public void updateLastPair() {
        Student student1;
        AtomicReference<Student> student2 = new AtomicReference<>();
        try {
            getTwoStudents();
            student1 = lastPair.get(0);
            student2.set(lastPair.get(1));
            getLastBattle(student1).ifPresent(battle -> {
                while (battle.getOpponent() == student2.get().getId()) {
                    student2.set(choosePersonSecondGroup());
                    if (secondSubGroup.size() == 1) {
                        break;
                    }
                }
            });
        } catch (Exception e) {
            student1 = lastPair.get(0);
            Student secondStudent = lastPair.get(1);
            if (secondStudent != null) {
                student2.set(lastPair.get(1));
            }
            if (student1 == null && student2.get() == null) {
                return;
            } else if (student1 == null) {
                lastPair.clear();
                lastPair.add(0, null);
                lastPair.add(1, student2.get());
                secondSubGroup.remove(student2.get());
                List<Battle> battles2 = student2.get().getBattles();
                Battle battle2 = new Battle(-1, 0);
                battles2.add(battle2);
                saved = false;
                return;
            } else {
                lastPair.clear();
                lastPair.add(0, student1);
                lastPair.add(1, null);
                firstSubGroup.remove(student1);
                List<Battle> battles1 = student1.getBattles();
                Battle battle1 = new Battle(-1, 0);
                battles1.add(battle1);
                saved = false;
                return;
            }
        }


        List<Battle> battles1 = student1.getBattles();
        Battle battle1 = new Battle(student2.get().getId(), 0);
        battles1.add(battle1);

        List<Battle> battles2 = student2.get().getBattles();
        Battle battle2 = new Battle(student1.getId(), 0);
        battles2.add(battle2);


        updateSubGroups(student1, student2.get());
        updateLastPair(student1, student2.get());
        saved = false;
    }

    private void getTwoStudents() {
        Student student1 = choosePersonFirstGroup();
        AtomicReference<Student> student2 = new AtomicReference<>(choosePersonSecondGroup());
        lastPair.add(student1);
        lastPair.add(student2.get());
        if (student1 == null || student2.get() == null) {
            throw new IllegalStateException("one of group is empty");
        }
    }

    private void updatePairStatistics(Student student1, Student student2) {
        if (!(student1 == null)) {
            groupOneCounter += getLastBattle(student1).get().getMark();
        }
        if (!(student2 == null)) {
            groupTwoCounter += getLastBattle(student2).get().getMark();
        }
    }


    public void saveBattlesLastPair() {
        if (lastPair.size() == 0) {
            return;
        }
        Student student1 = lastPair.get(0);
        Student student2 = lastPair.get(1);
        if (!(student1 == null)) {
            battleService.saveStudentBattle(getLastBattle(student1).get(), student1.getId());
            respondedUsers.add(lastPair.get(0));
        }
        if (!(student2 == null)) {
            battleService.saveStudentBattle(getLastBattle(student2).get(), student2.getId());
            respondedUsers.add(lastPair.get(1));
        }
        if (!lastPair.isEmpty()) {
            updatePairStatistics(lastPair.get(0), lastPair.get(1));
            saved = true;
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
            lastPair.clear();
        }
        lastPair.add(student1);
        lastPair.add(student2);
    }

    public Optional<Battle> getLastBattle(Student student) {
        if (student == null) return Optional.empty();
        List<Battle> battles = student.getBattles();
        if (battles.size() == 0) return Optional.empty();
        return Optional.ofNullable(battles.get(battles.size() - 1));
    }

    public Student choosePersonFirstGroup() {
        Random random = new Random();
        if (firstSubGroup.size() == 0) {
            return null;
        }
        int i = random.nextInt(firstSubGroup.size());
        return firstSubGroup.get(i);
    }

    public Student choosePersonSecondGroup() {
        Random random = new Random();
        if (secondSubGroup.size() == 0) {
            return null;
        }
        int i = random.nextInt(secondSubGroup.size());
        return secondSubGroup.get(i);
    }

    public Student getStudentFromAnywhere(String name) {
        try {
            return lastPair.stream().filter(Objects::nonNull).filter(x -> x.getName()
                    .equals(name)).findFirst().get();
        } catch (Exception e) {
            return respondedUsers.stream().filter(x -> x.getName()
                    .equals(name)).findFirst().get();
        }
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
        List<Battle> battles = studentByName.getBattles();
        Battle battle = new Battle(-1, 0);
        battles.add(battle);
        upsetStudents.add(studentByName);
        firstSubGroup.removeIf(x -> x.getName().equals(name));
        secondSubGroup.removeIf(x -> x.getName().equals(name));
    }

    public Student getStudentByName(String name) {
        ResultSet studentByName = jdbcStudentRepository.getStudentByName(name);
        List<Student> students = mapResultSetToStudents(studentByName);
        return students.get(0);
    }

    public double getAverageMark(int groupNumber) {
        return respondedUsers.stream()
                .filter(student -> student.getNumberOfGroup() == groupNumber)
                .mapToDouble(student -> ServletUtil.getMark(student, getInstance()))
                .average().getAsDouble();
    }

    public TwoSubGroups showFullStat() {
        ResultSet studentsByGroup1 = jdbcStudentRepository.getStudentsByGroup(1);
        ResultSet studentsByGroup2 = jdbcStudentRepository.getStudentsByGroup(2);
        List<Student> students1 = mapResultSetToStudents(studentsByGroup1);
        List<Student> students2 = mapResultSetToStudents(studentsByGroup2);
        return new TwoSubGroups(students1, students2);
    }

    public Student getStudentFromUpset(String name) {
        return upsetStudents.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }
}
