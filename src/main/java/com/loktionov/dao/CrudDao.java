package com.loktionov.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, ID> {

    void save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll(int page, int itemsPerPage);

    void update(E entity);

    void deleteById(ID id);
}
