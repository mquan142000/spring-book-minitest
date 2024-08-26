package com.example.springbookminitest.service.impl;

import com.example.springbookminitest.model.Book; // Correct import
import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.repository.IBookRepository;
import com.example.springbookminitest.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepository iBookRepository;

    @Override
    public Iterable<Book> findAll() {
        return iBookRepository.findAll();
    }

    @Override
    public void save(Book book) {
        iBookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return iBookRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iBookRepository.deleteById(id);
    }

    @Override
    public Iterable<Book> findAllByType(Type type) {
        return iBookRepository.findAllByType(type);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return iBookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findAllByNameContaining(Pageable pageable, String name) {
        return iBookRepository.findAllByNameContaining(pageable, name);
    }
}
