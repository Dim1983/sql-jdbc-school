package com.loktionov.dao;

import com.loktionov.entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseDaoTest {
    DataSource dataSource = new DataSource("/dbH2.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner(dataSource);
    CourseDao courseDao = new CourseDao(dataSource);

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
        Course expected = new Course(10, "TestCourse", "TestDescription");

        courseDao.save(expected);

        Course actual = courseDao.findById(10).get();

        assertEquals(expected, actual);
    }

    @Test
    void findByIdShouldBeReturnEntityFromDataBase() {
        Course expected = new Course(1, "Computer science", "Course of Computer science");

        Course actual = courseDao.findById(1).get();

        assertEquals(expected, actual);
    }

    @Test
    void deleteEntityShouldBeDeleteCourseFromDataBase() {
        courseDao.deleteById(9);

        Optional<Course> expected = Optional.empty();

        assertEquals(expected, courseDao.findById(9));
    }

    @Test
    void updateEntityShouldBeUpdateDateOfCourseIntoDataBase() {
        Course expected = new Course(9, "TestName", "TestDescription");

        courseDao.update(expected);

        assertEquals(expected, courseDao.findById(9).get());
    }

    @Test
    void findAllEntityShouldBeReturnEntityFromDataBase() {
        List<Course> expected = Arrays.asList(
                new Course(1, "Computer science", "Course of Computer science"),
                new Course(2, "Ecology", "Course of Ecology"),
                new Course(3, "Economics", "Course of Economics")
        );

        assertEquals(expected, courseDao.findAll(0, 3));
    }
}
