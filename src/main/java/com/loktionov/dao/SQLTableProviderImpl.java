package com.loktionov.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLTableProviderImpl implements SQLTableProvider {
    private static final Logger LOGGER = Logger.getLogger(SQLTableProviderImpl.class);

    private final DataSource dataSource;

    public SQLTableProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean checkExistsDataOfTable(String tableName) {
        String SQL = "SELECT EXISTS (SELECT 1 FROM " + tableName + ");";
        boolean flag = false;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                flag = resultSet.getBoolean("exists");
            }

            return flag;
        } catch (SQLException e) {
            LOGGER.error("Error of connection in SQL", e);

            throw new DataBaseRuntimeException();
        }
    }
}
