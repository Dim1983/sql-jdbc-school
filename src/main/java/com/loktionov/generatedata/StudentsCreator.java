package com.loktionov.generatedata;

import com.loktionov.entity.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentsCreator {
    private static final List<String> FIRST_NAMES = Arrays.asList("Mic", "Ben", "Tom", "Bred", "Pol", "John", "Oliver",
            "Jacob", "James", "Oscar", "George", "Alfie", "William", "Harry", "Samuel", "Daniel", "Frankie", "Luke",
            "Toby", "Blake");
    private static final List<String> SECOND_NAMES = Arrays.asList("Abramson", "Allford", "Arthurs", "Backer",
            "Berrington", "Chandter", "Chesterton", "Dean", "Dodson", "Erickson", "Fitzgerald", "Ford", "Freeman",
            "Garrison", "Little", "MacDonald", "Marshman", "Ogden", "Ramacey", "Stephen");

    private static final int LIMIT_GROUP_QUANTITY = 10;
    private static final int QUANTITY_OF_FIRST_NAMES = 20;
    private static final int QUANTITY_OF_SECOND_NAMES = 20;
    private static final int LIMIT_QUANTITY_ENTITY = 200;

    private final Random random;

    public StudentsCreator(Random random) {
        this.random = random;
    }

    public List<Student> createRandomStudentsName() {
        return Stream.generate(() -> Student.builder()
                .withGroupId(random.nextInt(LIMIT_GROUP_QUANTITY))
                .withFirstName(FIRST_NAMES.get(random.nextInt(QUANTITY_OF_FIRST_NAMES)))
                .withSecondName(SECOND_NAMES.get(random.nextInt(QUANTITY_OF_SECOND_NAMES)))
                .build())
                .limit(LIMIT_QUANTITY_ENTITY)
                .collect(Collectors.toList());
    }
}
