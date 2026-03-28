package com.backend.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.demo.dto.BookDto;
import com.backend.demo.dto.response.BookResponse;
import com.backend.demo.service.BookService;

@RestController
@RequestMapping({"/api/books", "/api/v1/books"})
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable("id") Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody BookDto bookDto) {
        BookResponse book = bookService.create(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Book berhasil ditambahkan", "data", book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateData(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        BookResponse updated = bookService.update(id, bookDto);
        return ResponseEntity.ok(Map.of("message", "Book berhasil diupdate", "data", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDate(@PathVariable("id") Long id) {
        BookResponse removed = bookService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Book berhasil dihapus", "data", removed));
    }
}
