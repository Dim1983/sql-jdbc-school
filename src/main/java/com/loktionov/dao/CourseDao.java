package com.loktionov.dao;

import com.loktionov.entity.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao extends AbstractCrudDaoImpl<Course> {
    private static final String ADD_NEW_ENTITY = "INSERT INTO COURSES (course_name, course_description) values (?, ?);";
    private static final String FIND_ENTITY = "SELECT * FROM COURSES WHERE course_id = ?;";
    private static final String DELETE_ENTITY = "DELETE FROM COURSES WHERE course_id = ?;";
    private static final String UPDATE_ENTITY = "UPDATE COURSES SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String FIND_ALL_ENTITIES = "SELECT * FROM courses limit ? offset ?;";

    public CourseDao(DataSource dataSource) {
        super(dataSource, FIND_ENTITY, ADD_NEW_ENTITY, DELETE_ENTITY, UPDATE_ENTITY, FIND_ALL_ENTITIES);
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Course(resultSet.getInt("course_id"),
                resultSet.getString("course_name"),
                resultSet.getString("course_description"));
    }

    @Override
    public void insertSave(PreparedStatement statement, Course entity) throws SQLException {
        statement.setString(1, entity.getCourseName());
        statement.setString(2, entity.getCourseDescription());
    }

    @Override
    public void insertUpdate(PreparedStatement statement, Course entity) throws SQLException {
        statement.setString(1, entity.getCourseName());
        statement.setString(2, entity.getCourseDescription());
        statement.setInt(3, entity.getCourseId());
    }
}
