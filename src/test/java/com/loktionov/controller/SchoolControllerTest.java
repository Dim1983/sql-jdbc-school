package com.loktionov.controller;

import com.loktionov.dao.GroupDao;
import com.loktionov.dao.StudentDao;
import com.loktionov.entity.Student;
import com.loktionov.validation.Validator;
import com.loktionov.view.VieweProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {
    @Mock
    private VieweProvider viewMock;

    @Mock
    private StudentDao studentDaoMock;

    @Mock
    private GroupDao groupDaoMock;

    @Mock
    private Validator validatorMock;

    @InjectMocks
    private SchoolController schoolController;

    @Test
    void choseControllerShouldBeReturnGroupsWithLessQuantityStudents() {
        List<Integer> quantityStudentsOnCourses = Collections.singletonList(1);

        when(viewMock.mainChooseUsers()).thenReturn(1);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(groupDaoMock.findLessQuantityStudents(anyInt())).thenReturn(quantityStudentsOnCourses);
        doNothing().when(viewMock).showLessGroupsWithStudents(quantityStudentsOnCourses);

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(groupDaoMock).findLessQuantityStudents(anyInt());
        verify(viewMock).showLessGroupsWithStudents(quantityStudentsOnCourses);
    }

    @Test
    void choseControllerShouldBeReturnListOfStudentsOnCours() {
        when(viewMock.mainChooseUsers()).thenReturn(2);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(viewMock.chooseFoundAllStudentsOnCourse()).thenReturn(1);

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(viewMock).chooseFoundAllStudentsOnCourse();
    }

    @Test
    void choseControllerShouldAddStudentOnCourse() {
        Student student = Student.builder()
                .withGroupId(1)
                .withFirstName("John")
                .withSecondName("Doo")
                .build();

        when(viewMock.mainChooseUsers()).thenReturn(3);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(viewMock.askDataOfStudents()).thenReturn(student);
        doNothing().when(studentDaoMock).save(student);
        doNothing().when(viewMock).addStudent();

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(viewMock).askDataOfStudents();
        verify(studentDaoMock).save(student);
        verify(viewMock).addStudent();
    }

    @Test
    void choseControllerShouldBeDeleteStudentById() {
        when(viewMock.mainChooseUsers()).thenReturn(4);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(viewMock.chooseIdForDeleteStudent()).thenReturn(1);
        doNothing().when(studentDaoMock).deleteById(anyInt());
        doNothing().when(viewMock).deleteStudent();

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(viewMock).chooseIdForDeleteStudent();
        verify(studentDaoMock).deleteById(anyInt());
        verify(viewMock).deleteStudent();
    }

    @Test
    void choseControllerShouldBeAddStudentOnCourse() {
        Student student = Student.builder()
                .withStudentId(1)
                .withCourseId(1)
                .build();

        when(viewMock.mainChooseUsers()).thenReturn(5);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(viewMock.addStudentOnCourse()).thenReturn(student);
        doNothing().when(studentDaoMock).update(student);
        doNothing().when(viewMock).resultUpdateStudentOnCourses();

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(viewMock).addStudentOnCourse();
        verify(studentDaoMock).update(student);
        verify(viewMock).resultUpdateStudentOnCourses();
    }

    @Test
    void choseControllerShouldBeRemoveStudentFromCourse() {
        when(viewMock.mainChooseUsers()).thenReturn(6);
        doNothing().when(validatorMock).checkChooseUser(anyInt());
        when(viewMock.chooseForRemoveStudentFromCourses()).thenAnswer(invocationOnMock -> {
            return Student.builder()
                    .withStudentId(1)
                    .withCourseId(1).build();
        });

        schoolController.chooseController();

        verify(viewMock).mainChooseUsers();
        verify(validatorMock).checkChooseUser(anyInt());
        verify(viewMock).chooseForRemoveStudentFromCourses();
        verify(studentDaoMock).deleteStudentOnCourses(any());
    }

    @Test
    void choseControllerShouldBeThrowIllegalArgumentExceptionWhenDefault() {
        doThrow(IllegalArgumentException.class).when(viewMock).mainChooseUsers();

        assertThrows(IllegalArgumentException.class, () -> schoolController.chooseController());

        verifyNoMoreInteractions(studentDaoMock, validatorMock);
    }

    @Test
    void choseControllerShouldBeThrowIllegalArgumentExceptionWhenWhenChooseOfUserLMoreTen() {
        when(viewMock.mainChooseUsers()).thenReturn(101);

        doThrow(IllegalArgumentException.class).when(validatorMock).checkChooseUser(101);

        assertThrows(IllegalArgumentException.class, () -> schoolController.chooseController());

        verifyNoMoreInteractions(studentDaoMock, groupDaoMock, viewMock);
    }

    @Test
    void choseControllerShouldBeThrowIllegalArgumentExceptionWhenWhenChooseOfUserLessZero() {
        when(viewMock.mainChooseUsers()).thenReturn(8);

        doNothing().when(validatorMock).checkChooseUser(anyInt());

        assertThrows(IllegalArgumentException.class, () -> schoolController.chooseController());

        verifyNoMoreInteractions(studentDaoMock, groupDaoMock, viewMock);
    }
}
