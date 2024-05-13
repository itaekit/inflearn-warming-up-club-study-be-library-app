package com.warmingup.libraryapp.domain.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Book() {
    }

    public Book(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Book name cannot be null or empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
