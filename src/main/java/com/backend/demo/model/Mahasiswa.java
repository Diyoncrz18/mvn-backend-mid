package com.backend.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Mahasiswa extends Person {
    private String jurusan;

    public Mahasiswa(String id, String nama, String jurusan) {
        super(id, nama);
        this.jurusan = jurusan;
    }
}
