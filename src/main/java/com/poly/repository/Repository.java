package com.poly.repository;
import java.util.List;

public interface Repository<T> {
    List<T> findAll();

    T findById(Long id);

    void save(T model);

    void remove(T id);

    void update(Long id, T model);
}


