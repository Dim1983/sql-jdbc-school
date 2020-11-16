package com.loktionov.dao;

import com.loktionov.entity.Student;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentDao {
    private static final Logger LOGGER = Logger.getLogger(StudentDaoImpl.class);

    private static final Integer ENTITY_ON_PAGE = 10;

    private static final String FIND_ALL_ITEMS_IN_GROUPS = "with t as (SELECT * FROM STUDENTS_COURSES WHERE COURSE_ID = ?) " +
            "select distinct students.student_id, students.group_id, students.first_name, " +
            "students.last_name, t.course_id from t inner join students on t.student_id = students.student_id " +
            "LIMIT ? OFFSET ?;";
    private static String FIND_ALL_ITEMS_QUERY = "with t as(SELECT * FROM STUDENTS WHERE student_id = ?) " +
            "select * from t join students_courses on t.student_id = students_courses.student_id;";
    private static String ADD_NEW_STUDENTS = "INSERT INTO STUDENTS (group_id, first_name, last_name) VALUES (?, ?, ?);";
    private static String DELETE_STUDENTS = "DELETE FROM STUDENTS WHERE student_id = ?;";
    private static String UPDATE_STUDENTS = "UPDATE STUDENTS SET group_id = ?, first_name = ?, last_name = ? " +
            "WHERE student_id = ?;";
    private static final String FIND_ALL_ENTITIES = "SELECT * FROM students limit ? offset ?;";

    private static final String SAVE_STUDENT_ON_COURSES = "INSERT INTO students_courses (student_id, course_id) " +
            "VALUES (?, ?);";
    private final static String DELETE_STUDENTS_FROM_COURSES = "DELETE FROM STUDENTS_COURSES " +
            "WHERE student_id = ? AND course_id = ?;";

    public StudentDaoImpl(DataSource dataSource) {
        super(dataSource, FIND_ALL_ITEMS_QUERY, ADD_NEW_STUDENTS, DELETE_STUDENTS, UPDATE_STUDENTS, FIND_ALL_ENTITIES);
    }

    @Override
    protected Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .withStudentId(resultSet.getInt("student_id"))
                .withGroupId(resultSet.getInt("group_id"))
                .withFirstName(resultSet.getString("first_name"))
                .withSecondName(resultSet.getString("last_name"))
                .build();
    }

    @Override
    public void insertSave(PreparedStatement statement, Student user) throws SQLException {
        statement.setInt(1, user.getGroupId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
    }

    @Override
    public void insertUpdate(PreparedStatement statement, Student user) throws SQLException {
        statement.setInt(1, user.getGroupId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setInt(4, user.getStudentId());
    }

    @Override
    public void saveStudentOnCourses(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_STUDENT_ON_COURSES)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, student.getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error save into students_courses!", e);

            throw new DataBaseRuntimeException("Error save into students_courses!", e);
        }
    }

    @Override
    public void deleteStudentOnCourses(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_FROM_COURSES)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, student.getCourseId());
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Error of delete student on courses!", e);

            throw new DataBaseRuntimeException("Error of delete student on courses!", e);
        }
    }

    @Override
    public List<Student> findStudentsOnCourses(Integer courseId, Integer stepOfViewData) {
        List<Student> studentsOnGroup = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ITEMS_IN_GROUPS)) {
            statement.setInt(1, courseId);
            statement.setInt(2, ENTITY_ON_PAGE);
            statement.setInt(3, stepOfViewData);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentsOnGroup.add(Student.builder()
                        .withStudentId(resultSet.getInt("student_id"))
                        .withGroupId(resultSet.getInt("group_id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withSecondName(resultSet.getString("last_name"))
                        .withCourseId(resultSet.getInt("course_id"))
                        .build());
            }

            return studentsOnGroup;
        } catch (SQLException e) {
            LOGGER.error("Found not give result!", e);

            throw new DataBaseRuntimeException(e);
        }
    }
}
