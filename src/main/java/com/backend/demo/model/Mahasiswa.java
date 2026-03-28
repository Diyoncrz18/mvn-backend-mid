package com.backend.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mahasiswa")
public class Mahasiswa extends Person {
    @Column(nullable = false)
    private String jurusan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosen_id")
    private Dosen dosenWali;

    @ManyToMany
    @JoinTable(
            name = "mahasiswa_book",
            joinColumns = @JoinColumn(name = "mahasiswa_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new LinkedHashSet<>();

    public Mahasiswa(String id, String nama, String jurusan) {
        super(id, nama);
        this.jurusan = jurusan;
    }
}
