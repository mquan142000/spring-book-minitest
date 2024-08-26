package com.example.springbookminitest.service;

import java.util.Optional;

public interface IGenericService<T> {
    Iterable<T> findAll();

    void save(T t);

    Optional<T> findById(Long id);

    void remove(Long id);
}
