package com.loktionov.controller;

import com.loktionov.dao.CourseDao;
import com.loktionov.dao.GroupDaoImpl;
import com.loktionov.dao.SQLScriptRunner;
import com.loktionov.dao.SQLTableProvider;
import com.loktionov.dao.StudentDao;
import com.loktionov.generatedata.StudentsCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateDataControllerTest {
    @Mock
    private SQLScriptRunner sqlScriptRunnerMock;

    @Mock
    private StudentsCreator studentsCreatorMock;

    @Mock
    private StudentDao studentDaoMock;

    @Mock
    private CourseDao courseDaoMock;

    @Mock
    private GroupDaoImpl groupDaoMock;

    @Mock
    private SQLTableProvider sqlTableProviderMock;

    @Mock
    private Random random;

    @InjectMocks
    private GenerateDataController generateDataController;

    @Test
    void runCreateDataShouldBeAddCoursesOnDataBase() throws SQLException {
        doNothing().when(sqlScriptRunnerMock).runSQLScript(anyString());

        when(sqlTableProviderMock.checkExistsDataOfTable(anyString())).thenReturn(false);

        generateDataController.runCreateData();

        verify(sqlScriptRunnerMock).runSQLScript(anyString());
        verify(sqlTableProviderMock, times(4)).checkExistsDataOfTable(anyString());
        verify(studentsCreatorMock).createRandomStudentsName();
    }

    @Test
    void runCreateDataShouldBeNotAddDataInDataBaseIfTableExists() throws SQLException {
        doNothing().when(sqlScriptRunnerMock).runSQLScript(anyString());

        when(sqlTableProviderMock.checkExistsDataOfTable(anyString())).thenReturn(true);

        generateDataController.runCreateData();

        verify(sqlScriptRunnerMock).runSQLScript(anyString());
        verify(sqlTableProviderMock, times(4)).checkExistsDataOfTable(anyString());
    }
}
