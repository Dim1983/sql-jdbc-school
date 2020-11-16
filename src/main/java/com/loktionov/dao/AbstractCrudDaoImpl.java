package com.loktionov.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {
    private static final Logger LOGGER = Logger.getLogger(AbstractCrudDaoImpl.class);

    private static final String RESULT_OF_FOUND = "Found not give result!";
    private static final String RESULT_OF_UPDATE = "Update is failed!";
    private static final String RESULT_OF_INSERT = "Insertion is failed!";

    private final String readEntity;
    private final String saveEntity;
    private final String deleteEntity;
    private final String updateEntity;
    private final String findAllEntity;
    protected final DataSource dataSource;

    public AbstractCrudDaoImpl(DataSource dataSource, String readEntity, String addEntity, String deleteEntity,
                               String updateEntity, String findAllEntity) {
        this.readEntity = readEntity;
        this.saveEntity = addEntity;
        this.deleteEntity = deleteEntity;
        this.updateEntity = updateEntity;
        this.findAllEntity = findAllEntity;
        this.dataSource = dataSource;
    }

    @Override
    public Optional<E> findById(Integer entityId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(readEntity)) {
            statement.setInt(1, entityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error(RESULT_OF_FOUND, e);

            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void save(E entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveEntity)) {
            insertSave(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(RESULT_OF_INSERT, e);

            throw new DataBaseRuntimeException(RESULT_OF_INSERT, e);
        }
    }

    @Override
    public void deleteById(Integer entityId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteEntity)) {
            statement.setInt(1, entityId);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error(RESULT_OF_UPDATE, e);

            throw new DataBaseRuntimeException(RESULT_OF_UPDATE, e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateEntity)) {
            insertUpdate(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(RESULT_OF_UPDATE, e);

            throw new DataBaseRuntimeException(RESULT_OF_UPDATE, e);
        }
    }

    @Override
    public List<E> findAll(int page, int itemsPerPage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllEntity)) {
            List<E> entities = new ArrayList<>();
            statement.setInt(1, itemsPerPage);
            statement.setInt(2, page);
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            LOGGER.error(RESULT_OF_UPDATE, e);

            throw new DataBaseRuntimeException(RESULT_OF_UPDATE, e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    public abstract void insertSave(PreparedStatement statement, E entity) throws SQLException;

    public abstract void insertUpdate(PreparedStatement statement, E entity) throws SQLException;
}
