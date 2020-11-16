package com.loktionov.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLScriptRunner {
    private static final Logger LOGGER = Logger.getLogger(SQLScriptRunner.class);

    private final DataSource dataSource;

    public SQLScriptRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void runSQLScript(String filePath) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptRunner runner = new ScriptRunner(connection);
            try (Reader reader = new BufferedReader(new FileReader(String.valueOf(Paths.get(filePath)
                    .toAbsolutePath())))) {
                runner.runScript(reader);
            }
        } catch (SQLException | IOException e) {
            LOGGER.error("Error connection on DataBase!");

            throw new DataBaseRuntimeException("Error connection on DataBase!", e);
        }
    }
}
