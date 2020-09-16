package com.loktionov.dao;

import com.loktionov.entity.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Student, Integer> {
    void saveStudentOnCourses(Student student);

    void deleteStudentOnCourses(Student student);

    List<Student> findStudentsOnCourses(Integer courseId, Integer stepOfViewData);
}
