package com.loktionov.dao;

import com.loktionov.entity.Group;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl extends AbstractCrudDaoImpl<Group> implements GroupDao {
    private static final Logger LOGGER = Logger.getLogger(GroupDaoImpl.class);

    private static final String ADD_NEW_ENTITY = "INSERT INTO GROUPS (group_name) values (?);";
    private static final String FIND_ALL_ENTITY = "SELECT group_id, group_name FROM groups WHERE group_id = ?;";
    private static final String DELETE_ENTITY = "DELETE FROM GROUPS WHERE group_id = ?;";
    private static final String UPDATE_ENTITY = "UPDATE GROUPS SET group_name = ? WHERE group_id = ?;";
    private static final String FIND_LESS_OR_EQUALS_ENTITY_IN_GROUPS = "with t as (SELECT COUNT(group_id), group_id " +
            "FROM students GROUP BY group_id HAVING COUNT(group_id) <= ?)\n" +
            "select * from t inner join groups on t.group_id = groups.group_id;";
    private static final String FIND_ALL_ENTITIES = "SELECT * FROM groups limit ? offset ?;";

    public GroupDaoImpl(DataSource dataSource) {
        super(dataSource, FIND_ALL_ENTITY, ADD_NEW_ENTITY, DELETE_ENTITY, UPDATE_ENTITY, FIND_ALL_ENTITIES);
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt("group_id"),
                resultSet.getString("group_name"));
    }

    @Override
    public void insertSave(PreparedStatement statement, Group group) throws SQLException {
        statement.setString(1, group.getGroupName());
    }

    @Override
    public void insertUpdate(PreparedStatement statement, Group group) throws SQLException {
        statement.setString(1, group.getGroupName());
        statement.setInt(2, group.getGroupId());
    }

    @Override
    public List<Integer> findLessQuantityStudents(int studentsQuantity) {
        List<Integer> groupsWithLessQuantityStudents = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_LESS_OR_EQUALS_ENTITY_IN_GROUPS);
        ) {
            statement.setInt(1, studentsQuantity);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groupsWithLessQuantityStudents.add(resultSet.getInt("group_id"));
                }
            }
            return groupsWithLessQuantityStudents;
        } catch (SQLException e) {
            LOGGER.error("Found not give result!", e);

            throw new DataBaseRuntimeException(e);
        }
    }
}
