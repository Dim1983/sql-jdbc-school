package com.loktionov.controller;

import com.loktionov.dao.CourseDao;
import com.loktionov.dao.GroupDaoImpl;
import com.loktionov.dao.SQLScriptRunner;
import com.loktionov.dao.SQLTableProvider;
import com.loktionov.dao.StudentDao;
import com.loktionov.entity.Course;
import com.loktionov.entity.Group;
import com.loktionov.entity.Student;
import com.loktionov.generatedata.StudentsCreator;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GenerateDataController {
    private static final int LIMIT_QUANTITY_GROUP = 10;
    private static final int QUANTITY_STUDENTS = 200;
    private static final int QUANTITY_GROUPS = 10;
    private static final int ID_GROUPS_INCLUDE_ZERO = 11;
    private static final int LIMIT_GROUP_ID = 9;
    private static final int QUANTITY_OF_GENERATE_STUDENTS_COURSES = 9;
    private static final int INCREASE_ID_FOR_RESULT_ONE_TEN = 1;
    private static final int STUDENT_ID_START_NUMBER = 1;

    private static final String NAME_TABLE_COURSES = "courses";
    private static final String NAME_TABLE_STUDENTS = "students";
    private static final String NAME_TABLE_GROUPS = "groups";
    private static final String NAME_TABLE_STUDENT_COURSES = "students_courses";
    private static final String CONNECTOR_OF_GROUP_NAME = "-";

    private final SQLScriptRunner sqlScriptRunner;
    private final SQLTableProvider sqlTableProvider;
    private final StudentsCreator studentsCreator;
    private final Random random;
    private final StudentDao studentDao;
    private final GroupDaoImpl groupDao;
    private final CourseDao courseDao;

    public GenerateDataController(SQLScriptRunner sqlScriptRunner, SQLTableProvider sqlTableProvider,
                                  StudentsCreator studentsCreator, Random random, StudentDao studentDao,
                                  GroupDaoImpl groupDao, CourseDao courseDao) {
        this.sqlScriptRunner = sqlScriptRunner;
        this.sqlTableProvider = sqlTableProvider;
        this.studentsCreator = studentsCreator;
        this.random = random;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
    }

    public void runCreateData() throws SQLException {
        sqlScriptRunner.runSQLScript("src/main/resources/createtable.sql");

        if (!sqlTableProvider.checkExistsDataOfTable(NAME_TABLE_COURSES)) {
            addCourses();
        }

        if (!sqlTableProvider.checkExistsDataOfTable(NAME_TABLE_STUDENTS)) {
            List<Student> students = studentsCreator.createRandomStudentsName();
            students.forEach(studentDao::save);
        }

        if (!sqlTableProvider.checkExistsDataOfTable(NAME_TABLE_GROUPS)) {
            addGroupName();
        }

        if (!sqlTableProvider.checkExistsDataOfTable(NAME_TABLE_STUDENT_COURSES)) {
            addRandomCoursesOfStudents();
        }
    }

    private void addGroupName() {
        Stream.generate(() -> RandomStringUtils.random(2, true, false)
                .toLowerCase()
                + CONNECTOR_OF_GROUP_NAME + RandomStringUtils.random(2, false, true))
                .limit(LIMIT_QUANTITY_GROUP)
                .forEach((groupName) -> groupDao.save(new Group(random.nextInt(LIMIT_GROUP_ID) +
                        INCREASE_ID_FOR_RESULT_ONE_TEN, groupName)));
    }

    private void addCourses() {
        Map<String, String> coursesNameAndDescription = new HashMap<String, String>() {{
            put("Math", "Course of Mathematic");
            put("Computer science", "Computer science");
            put("Ecology", "Course of Ecology");
            put("Economics", "Course of Economics");
            put("Geography", "Course of Geography");
            put("History", "Course of History");
            put("Literature", "Course of Literature");
            put("Music", "Course of Music");
            put("Natural history", "Course of Natural history");
            put("Philosophy", "Course of Natural history Philosophy");
        }};

        coursesNameAndDescription.forEach((key, value) ->
                courseDao.save(new Course(random.nextInt(QUANTITY_GROUPS) +
                        INCREASE_ID_FOR_RESULT_ONE_TEN, key, value)));
    }

    private void addRandomCoursesOfStudents() {
        AtomicInteger count = new AtomicInteger(STUDENT_ID_START_NUMBER);
        Stream.generate(count::getAndIncrement)
                .limit(QUANTITY_STUDENTS)
                .forEach((studentId) -> Stream.generate(() -> Student.builder()
                        .withStudentId(count.getAndIncrement())
                        .withCourseId(random.nextInt(QUANTITY_GROUPS) + INCREASE_ID_FOR_RESULT_ONE_TEN)
                        .build())
                        .limit(QUANTITY_OF_GENERATE_STUDENTS_COURSES)
                        .forEach(studentDao::saveStudentOnCourses));
    }
}
