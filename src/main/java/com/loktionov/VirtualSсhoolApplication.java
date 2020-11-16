package com.loktionov;

import com.loktionov.controller.GenerateDataController;
import com.loktionov.controller.SchoolController;
import com.loktionov.dao.CourseDao;
import com.loktionov.dao.DataSource;
import com.loktionov.dao.GroupDaoImpl;
import com.loktionov.dao.SQLScriptRunner;
import com.loktionov.dao.SQLTableProvider;
import com.loktionov.dao.SQLTableProviderImpl;
import com.loktionov.dao.StudentDao;
import com.loktionov.dao.StudentDaoImpl;
import com.loktionov.generatedata.StudentsCreator;
import com.loktionov.validation.Validator;
import com.loktionov.view.VieweProvider;

import java.sql.SQLException;
import java.util.Random;

public class VirtualSсhoolApplication {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSource("/db.properties");
        Random random = new Random();
        SQLScriptRunner sqlScriptRunner = new SQLScriptRunner(dataSource);
        SQLTableProvider sqlTableProvider = new SQLTableProviderImpl(dataSource);
        StudentsCreator studentsCreator = new StudentsCreator(random );
        VieweProvider viewer = new VieweProvider();
        Validator validator = new Validator();
        StudentDao studentDao = new StudentDaoImpl(dataSource);
        GroupDaoImpl groupDao = new GroupDaoImpl(dataSource);
        CourseDao courseDao = new CourseDao(dataSource);

        GenerateDataController generateDataController = new GenerateDataController(sqlScriptRunner,
                sqlTableProvider, studentsCreator, random, studentDao, groupDao,courseDao);
        generateDataController.runCreateData();

        SchoolController schoolController = new SchoolController(viewer, studentDao, groupDao,
                courseDao, validator);
        schoolController.chooseController();
    }
}
