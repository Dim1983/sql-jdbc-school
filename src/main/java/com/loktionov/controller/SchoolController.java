package com.loktionov.controller;

import com.loktionov.dao.CourseDao;
import com.loktionov.dao.GroupDao;
import com.loktionov.dao.StudentDao;
import com.loktionov.entity.Student;
import com.loktionov.validation.Validator;
import com.loktionov.view.VieweProvider;

import java.util.List;

public class SchoolController {
    private static final int STEP_QUANTITY_DATE_OF_PAGE = 10;

    private final VieweProvider view;
    private final Validator validator;
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;

    public SchoolController(VieweProvider view, StudentDao studentDao, GroupDao groupDao,
                            CourseDao courseDao, Validator validator) {
        this.view = view;
        this.studentDao = studentDao;
        this.validator = validator;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
    }

    public void chooseController() {
        int chooseOfUser = view.mainChooseUsers();
        validator.checkChooseUser(chooseOfUser);
        switch (chooseOfUser) {
            case 1:
                int quantityStudentsOnGroup = view.readQuantityStudentsOnGroups();
                view.showLessGroupsWithStudents(groupDao.findLessQuantityStudents(quantityStudentsOnGroup));
                break;
            case 2:
                int courseId = view.chooseFoundAllStudentsOnCourse();
                getStepOfPageStudents(courseId);
                break;
            case 3:
                Student student = view.askDataOfStudents();
                studentDao.save(student);
                view.addStudent();
                break;
            case 4:
                int idStudent = view.chooseIdForDeleteStudent();
                studentDao.deleteById(idStudent);
                view.deleteStudent();
                break;
            case 5:
                studentDao.update(view.addStudentOnCourse());
                view.resultUpdateStudentOnCourses();
                break;
            case 6:
                Student dataOfStudentForRemoveCourse = view.chooseForRemoveStudentFromCourses();
                studentDao.deleteStudentOnCourses(dataOfStudentForRemoveCourse);
                break;
            case 7:
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void getStepOfPageStudents(int courseId) {
        int lineOfPage = 10;
        boolean exit = true;
        while (exit) {
            List<Student> students = studentDao.findStudentsOnCourses(courseId, lineOfPage);
            if (students.isEmpty()) {
                System.out.println("End list!");
                exit = false;
            }
            view.showAllStudentsOnCourse(students);

            lineOfPage = lineOfPage + STEP_QUANTITY_DATE_OF_PAGE;
        }
    }
}
