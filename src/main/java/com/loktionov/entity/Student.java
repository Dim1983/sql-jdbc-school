package com.loktionov.entity;

import java.util.Objects;

public class Student {
    private final Integer studentId;
    private final Integer groupId;
    private final String firstName;
    private final String lastName;
    private final Integer courseId;

    private Student(StudentBuilder builder) {
        this.studentId = builder.studentId;
        this.groupId = builder.groupId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.courseId = builder.courseId;
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getGroupId() {
        return groupId;
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
        Student student = (Student) o;
        return studentId == student.studentId &&
                groupId == student.groupId &&
                courseId == student.courseId &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, groupId, firstName, lastName, courseId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", groupId=" + groupId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courseId=" + courseId +
                '}';
    }

    public static class StudentBuilder {
        private int studentId;
        private String firstName;
        private String lastName;
        private Integer groupId;
        private Integer courseId;

        private StudentBuilder() {
        }

        public StudentBuilder withStudentId(int studentId) {
            this.studentId = studentId;
            return this;
        }

        public StudentBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentBuilder withSecondName(String secondName) {
            this.lastName = secondName;
            return this;
        }

        public StudentBuilder withGroupId(Integer groupId) {
            this.groupId = groupId;
            return this;
        }

        public StudentBuilder withCourseId(Integer courseId) {
            this.courseId = courseId;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
