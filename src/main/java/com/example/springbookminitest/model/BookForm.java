package com.example.springbookminitest.model;

import org.springframework.web.multipart.MultipartFile;

public class BookForm {
    private Long id;
    private String name;
    private String author;
    private double price;
    private MultipartFile image;
    private Type type;

    public BookForm() {

    }

    public BookForm(Long id, String name, String author, double price, MultipartFile image, Type type) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.image = image;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
