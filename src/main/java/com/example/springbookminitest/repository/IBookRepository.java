package com.example.springbookminitest.repository;


import com.example.springbookminitest.model.Book;
import com.example.springbookminitest.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface IBookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAllByType(Type type);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByNameContaining(Pageable pageable, String name);

}
