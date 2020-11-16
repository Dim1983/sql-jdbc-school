package com.loktionov.dao;

import com.loktionov.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentDaoImplTest {
    DataSource dataSource = new DataSource("/dbH2.properties");
    SQLScriptRunner sqlScriptRunner = new SQLScriptRunner(dataSource);
    StudentDao studentDao = new StudentDaoImpl(dataSource);

    @BeforeEach
    void generateTestData() {
        sqlScriptRunner.runSQLScript("src/test/resources/createsqltabletest.sql");
    }

    @AfterEach
    void cleanTestData() {
        sqlScriptRunner.runSQLScript("src/test/resources/cleantestdata.sql");
    }

    @Test
    void saveShouldBeSaveStudentOnDataBase() {
        Optional<Student> expected = Optional.of(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .build());

        studentDao.saveStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(5)
                .build());

        Optional<Student> actual = studentDao.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    void readEntityShouldBeReturnStudentsFromDataBase() {
        studentDao.saveStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build());

        Student expected = Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .build();


        Student actual = studentDao.findById(1).get();

        assertEquals(expected, actual);
    }

    @Test
    void deleteEntityShouldBeDeleteStudentFromDataBase() {
        studentDao.deleteById(1);

        Optional<Student> expected = Optional.of(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .build());

        Optional<Student> actual = studentDao.findById(1);

        assertNotEquals(expected, actual);
    }

    @Test
    void updateEntityShouldBeUpdateStudentFromDataBase() {
        Student expected = Student.builder()
                .withStudentId(1)
                .withGroupId(5)
                .withFirstName("Entony")
                .withSecondName("Joshua")
                .build();

        studentDao.saveStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(5)
                .withFirstName("Entony")
                .withSecondName("Joshua")
                .withCourseId(5)
                .build());

        studentDao.update(expected);

        Student actual = studentDao.findById(1).get();

        assertEquals(expected, actual);
    }

    @Test
    void findStudentsOnCoursesShouldBeReturnStudentsOnCourse() {
        List<Student> expected = Collections.singletonList(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build());

        Student student = Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build();
        studentDao.saveStudentOnCourses(student);

        studentDao.save(student);

        assertEquals(expected, studentDao.findStudentsOnCourses(1, 0));
    }

    @Test
    void findAllEntityShouldBeReturnEntityFromDataBase() {
        List<Student> expected = Collections.singletonList(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .build());

        assertEquals(expected, studentDao.findAll(0, 1));
    }

    @Test
    void saveStudentsOnCoursesShouldBeAddedStudentOnCourse() {
        studentDao.saveStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build());

        Optional<Student> expected = Optional.of(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .build());

        assertEquals(expected, studentDao.findById(1));
    }

    @Test
    void deleteStudentOnCoursesShouldBeReturnNull() {
        studentDao.saveStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build());

        studentDao.deleteStudentOnCourses(Student.builder()
                .withStudentId(1)
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Smith")
                .withCourseId(1)
                .build());

        Optional<Student> expected = Optional.empty();

        assertEquals(expected, studentDao.findById(1));
    }
}

