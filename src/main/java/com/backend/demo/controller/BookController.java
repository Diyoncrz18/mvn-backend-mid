package com.backend.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.demo.dto.BookDto;
import com.backend.demo.model.Book;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    List<Book> books = new ArrayList<>();

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @PostMapping
    public Book createBook(@RequestBody BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());

        books.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public List<Book> updateData(@PathVariable("id") Long id, @RequestBody BookDto bookDto){
        for (Book book : books) {
            if (Objects.equals(book.getId(), id)) {
                book.setAuthor(bookDto.getAuthor());
                book.setTitle(bookDto.getTitle());
                book.setIsbn(bookDto.getIsbn());
            }
        }
        return books;
    }

    @DeleteMapping("/{id}")
    public List<Book> deleteDate(@PathVariable("id") Long id){
        books.removeIf(book -> Objects.equals(book.getId(), id));
        return books;
    }
}