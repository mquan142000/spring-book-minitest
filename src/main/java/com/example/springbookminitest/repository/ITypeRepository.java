package com.example.springbookminitest.repository;

import com.example.springbookminitest.model.DTO.ICountBook;
import com.example.springbookminitest.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ITypeRepository extends JpaRepository<Type, Long> {
    @Query(nativeQuery = true, value = "SELECT type.name, count(book.id) as number FROM type LEFT JOIN book on type.id = type_id GROUP BY type.name ;")
    Iterable<ICountBook> getCountBooks();

    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "call deletetypebyid(:id)")
    void deleteTypeById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "call deletetypebyid(:id)")
    void xoaTypeById(@Param("id") Long id);
}
