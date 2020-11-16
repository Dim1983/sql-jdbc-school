package com.loktionov.generatedata;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class StudentsCreatorTest {
    private final Random random = new Random();
    private final StudentsCreator studentsCreatorTest = new StudentsCreator(random);

    @Test
    void createRandomStudentsNameShouldBeReturnStudentsList() {

        assertThat(studentsCreatorTest.createRandomStudentsName(), hasSize(200));
    }
}
