package com.loktionov.dao;

import com.loktionov.entity.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class GroupDaoImplTest {
    DataSource dataSource = new DataSource("/dbH2.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner(dataSource);
    GroupDaoImpl groupDao = new GroupDaoImpl(dataSource);

    @BeforeEach
    void generateTestData() {
        sqlScriptRunner.runSQLScript("src/test/resources/createsqltabletest.sql");
    }

    @AfterEach
    void cleanTestData() {
        sqlScriptRunner.runSQLScript("src/test/resources/cleantestdata.sql");
    }

    @Test
    void saveShouldBeAddNewEntityIntoDataBase() {
        Group expected = new Group(6, "f");

        groupDao.save(expected);

        assertEquals(expected, groupDao.findById(6).get());
    }

    @Test
    void readEntityShouldBeReturnEntityFromDataBase() {
        Group expected = new Group(6, "f");

        groupDao.save(expected);

        assertEquals(expected, groupDao.findById(6).get());
    }

    @Test
    void deleteEntityShouldBeDeleteEntityFromDataBase() {
        groupDao.deleteById(5);

        Optional<Group> expected = Optional.empty();

        assertEquals(expected, groupDao.findById(5));
    }

    @Test
    void updateEntityShouldBeUpdateDataIntoDatabase() {
        Group expected = new Group(5, "Test");

        groupDao.update(expected);

        assertEquals(expected, groupDao.findById(5).get());
    }

    @Test
    void findLessQuantityStudentsShouldBeReturnGroupsWithLessQuantityStudents() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);

        assertEquals(expected, groupDao.findLessQuantityStudents(1));
    }

    @Test
    void findAllEntityShouldBeReturnAllEntityFromDataBase() {
        List<Group> expected = Arrays.asList(
                new Group(1, "a"),
                new Group(2, "b"),
                new Group(3, "c"),
                new Group(4, "d"));

        assertEquals(expected, groupDao.findAll(0, 4));
    }

    @Test
    void findAllEntityShouldBeReturnAllEntity() {
        GroupDaoImpl groupDao = mock(GroupDaoImpl.class);

        doThrow(new DataBaseRuntimeException()).when(groupDao).findLessQuantityStudents(anyInt());

        assertThrows(DataBaseRuntimeException.class, () -> {
            groupDao.findLessQuantityStudents(anyInt());
        });
    }
}
