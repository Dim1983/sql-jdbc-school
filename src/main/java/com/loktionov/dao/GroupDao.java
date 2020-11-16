package com.loktionov.dao;

import java.util.List;

public interface GroupDao {
    List<Integer> findLessQuantityStudents(int quantityStudents);
}
