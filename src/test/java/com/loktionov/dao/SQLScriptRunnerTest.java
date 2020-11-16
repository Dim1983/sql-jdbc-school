package com.loktionov.dao;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SQLScriptRunnerTest {
    DataSource dataSource = new DataSource("/dbH2.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner(dataSource);

    @Test
    void runSQLScriptShouldBeExceptionWhenPathIllegal() {
        DataBaseRuntimeException thrown = assertThrows(DataBaseRuntimeException.class,
                () -> sqlScriptRunner.runSQLScript("test.path"));
        assertThat("Error connection on DataBase!", is(thrown.getMessage()));
    }
}
