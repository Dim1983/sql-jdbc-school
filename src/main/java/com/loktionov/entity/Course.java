package com.loktionov.entity;

import java.util.Objects;

public class Course {
    private final Integer courseId;
    private final String courseName;
    private final String courseDescription;

    public Course(Integer courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseDescription, course.courseDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseDescription);
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + courseId +
                ", course_name='" + courseName + '\'' +
                ", course_description='" + courseDescription + '\'' +
                '}';
    }
}
