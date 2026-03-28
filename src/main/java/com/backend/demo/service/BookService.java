package com.backend.demo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.BookDto;
import com.backend.demo.dto.response.BookResponse;
import com.backend.demo.mapper.RelationMapper;
import com.backend.demo.model.Book;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.repository.BookRepository;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponse> getAll() {
        return bookRepository.findAllByOrderByIdAsc().stream()
                .map(RelationMapper::toBookResponse)
                .toList();
    }

    public BookResponse getById(Long id) {
        return RelationMapper.toBookResponse(findEntityById(id));
    }

    @Transactional
    public BookResponse create(BookDto dto) {
        Long id = requireId(dto.getId());
        if (bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book dengan ID tersebut sudah ada");
        }

        Book book = new Book(
                id,
                requireText(dto.getTitle(), "Judul book"),
                requireText(dto.getAuthor(), "Author"),
                requireText(dto.getIsbn(), "ISBN"));
        return RelationMapper.toBookResponse(bookRepository.save(book));
    }

    @Transactional
    public BookResponse update(Long id, BookDto dto) {
        Book book = findEntityById(id);
        book.setTitle(requireText(dto.getTitle(), "Judul book"));
        book.setAuthor(requireText(dto.getAuthor(), "Author"));
        book.setIsbn(requireText(dto.getIsbn(), "ISBN"));
        return RelationMapper.toBookResponse(book);
    }

    @Transactional
    public BookResponse delete(Long id) {
        Book book = findEntityById(id);
        BookResponse response = RelationMapper.toBookResponse(book);

        for (Mahasiswa mahasiswa : new LinkedHashSet<>(book.getMahasiswa())) {
            mahasiswa.getBooks().remove(book);
        }

        bookRepository.delete(book);
        return response;
    }

    private Book findEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book dengan ID '" + id + "' tidak ditemukan"));
    }

    private Long requireId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID book wajib diisi");
        }
        return id;
    }

    private String requireText(String value, String fieldName) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " wajib diisi");
        }
        return value.trim();
    }
}
