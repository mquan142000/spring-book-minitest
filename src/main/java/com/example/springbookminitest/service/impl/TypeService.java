package com.example.springbookminitest.service.impl;

import com.example.springbookminitest.model.DTO.ICountBook;
import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.repository.ITypeRepository;
import com.example.springbookminitest.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeService implements ITypeService {

    @Autowired

    private ITypeRepository iTypeRepository;

    @Override
    public Iterable<Type> findAll() {
        return iTypeRepository.findAll();
    }

    @Override
    public void save(Type type) {
        iTypeRepository.save(type);
    }

    @Override
    public Optional<Type> findById(Long id) {
        return iTypeRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iTypeRepository.deleteById(id);
    }

    @Override
    public void deleteTypeById(Long id) {
        iTypeRepository.xoaTypeById(id);
    }

    @Override
    public Iterable<ICountBook> getCountBooks() {
        return iTypeRepository.getCountBooks();
    }

}