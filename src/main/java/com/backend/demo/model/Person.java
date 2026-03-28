package com.backend.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    @Id
    @Column(length = 20, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String nama;

    protected Person(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
}
