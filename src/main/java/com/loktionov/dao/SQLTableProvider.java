package com.loktionov.dao;

import java.sql.SQLException;

public interface SQLTableProvider {
    boolean checkExistsDataOfTable(String tableName) throws SQLException;
}
