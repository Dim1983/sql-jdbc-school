package com.loktionov.view;

import com.loktionov.entity.Student;

import java.util.List;
import java.util.Scanner;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class VieweProvider {
    private static final IntSupplier CHOOSE_OF_USER_NUMBER = () -> {
        Scanner inputData = new Scanner(System.in);
        return inputData.nextInt();
    };

    private static final Supplier<String> CHOOSE_OF_USER_STRING = () -> {
        Scanner inputData = new Scanner(System.in);
        return inputData.next();
    };

    public int mainChooseUsers() {
        System.out.println("Choose action!\n" +
                "1. Found groups with less quantity of students!\n" +
                "2. Found all students on course!\n" +
                "3. Add new students!\n" +
                "4. Delete students!\n" +
                "5. Add students on course!\n" +
                "6. Delete students with course!\n" +
                "7. Exit!\n");

        return CHOOSE_OF_USER_NUMBER.getAsInt();
    }

    public void showLessGroupsWithStudents(List<Integer> quantityStudentsInGroups) {
        quantityStudentsInGroups.stream()
                .map(groupNumber -> String.format("%s %s", "Number groups with need students: ", groupNumber))
                .forEach(System.out::println);
    }

    public int chooseFoundAllStudentsOnCourse() {
        System.out.println("Input course ID!");
        return CHOOSE_OF_USER_NUMBER.getAsInt();
    }

    public int readQuantityStudentsOnGroups() {
        System.out.println("Input course ID!");
        return CHOOSE_OF_USER_NUMBER.getAsInt();
    }

    public int chooseIdForDeleteStudent() {
        System.out.println("Input student ID!");
        return CHOOSE_OF_USER_NUMBER.getAsInt();
    }

    public Student chooseForRemoveStudentFromCourses() {
        System.out.println("Input student ID and course ID");

        return Student.builder()
                .withStudentId(CHOOSE_OF_USER_NUMBER.getAsInt())
                .withCourseId(CHOOSE_OF_USER_NUMBER.getAsInt())
                .build();
    }

    public void showAllStudentsOnCourse(List<Student> studentsOnCourse) {
        studentsOnCourse.stream()
                .map(s -> String.format("%d %s %s %d", s.getStudentId(), s.getFirstName(),
                        s.getLastName(), s.getCourseId()))
                .forEach(System.out::println);
    }

    public Student askDataOfStudents() {
        System.out.println("Input number of group, First Name, Last Name");
        return Student.builder()
                .withGroupId(CHOOSE_OF_USER_NUMBER.getAsInt())
                .withFirstName(CHOOSE_OF_USER_STRING.get())
                .withSecondName(CHOOSE_OF_USER_STRING.get())
                .build();
    }

    public void addStudent() {
        System.out.println("Student added!");
    }

    public void deleteStudent() {
        System.out.println("Student deleted!");
    }

    public Student addStudentOnCourse() {
        System.out.println("Input student ID, Course ID");
        return Student.builder()
                .withStudentId(CHOOSE_OF_USER_NUMBER.getAsInt())
                .withCourseId(CHOOSE_OF_USER_NUMBER.getAsInt())
                .build();
    }

    public void resultUpdateStudentOnCourses() {
        System.out.println("You updated data of student!");
    }
}
