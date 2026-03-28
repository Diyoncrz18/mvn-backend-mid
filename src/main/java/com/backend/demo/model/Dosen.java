package com.backend.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dosen")
public class Dosen extends Person {
    @Column(nullable = false)
    private String keahlian;

    @OneToMany(mappedBy = "dosenWali")
    private List<Mahasiswa> mahasiswaDibimbing = new ArrayList<>();

    public Dosen(String id, String nama, String keahlian) {
        super(id, nama);
        this.keahlian = keahlian;
    }
}
