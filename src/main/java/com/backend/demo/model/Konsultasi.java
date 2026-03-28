package com.backend.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "konsultasi")
public class Konsultasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dosen_id", nullable = false)
    private Dosen dosen;

    @Column(nullable = false)
    private String topik;

    @Column(nullable = false)
    private String tanggal;

    @Column(nullable = false)
    private String waktu;

    public Konsultasi(Mahasiswa mahasiswa, Dosen dosen, String topik, String tanggal, String waktu) {
        this.mahasiswa = mahasiswa;
        this.dosen = dosen;
        this.topik = topik;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }
}
