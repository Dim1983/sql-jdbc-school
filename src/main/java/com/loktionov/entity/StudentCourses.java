package com.loktionov.entity;

import java.util.Objects;

public class StudentCourses {
    private final Integer studentId;
    private final Integer courseId;

    public StudentCourses(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentCourses that = (StudentCourses) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "StudentCourses{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
