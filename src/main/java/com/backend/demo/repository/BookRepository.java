package com.backend.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByIdAsc();
}
