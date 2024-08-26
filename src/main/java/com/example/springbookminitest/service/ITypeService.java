package com.example.springbookminitest.service;

import com.example.springbookminitest.model.DTO.ICountBook;
import com.example.springbookminitest.model.Type;

import java.util.List;

public interface ITypeService extends IGenericService<Type> {
    void deleteTypeById(Long id);

    Iterable<ICountBook> getCountBooks();

}
