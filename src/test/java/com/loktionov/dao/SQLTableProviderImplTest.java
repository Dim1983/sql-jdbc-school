package com.loktionov.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class SQLTableProviderImplTest {
    DataSource dataSource = new DataSource("/db.properties");
    SQLTableProvider sqlTableProvider = new SQLTableProviderImpl(dataSource);

    @Test
    void checkExistsDataOfTableTest() throws SQLException {
        boolean expected = sqlTableProvider.checkExistsDataOfTable("groups");
        Assertions.assertEquals(true, expected);
    }

    @Test
    void checkExistsDataOfTable() throws SQLException {
        DataBaseRuntimeException thrown = Assertions.assertThrows(DataBaseRuntimeException.class,
                ()->sqlTableProvider.checkExistsDataOfTable("group"));
    }
}