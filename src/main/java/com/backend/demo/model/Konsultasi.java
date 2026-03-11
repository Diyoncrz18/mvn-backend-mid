package com.backend.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Konsultasi {
    private Mahasiswa mahasiswa;
    private Dosen dosen;
    private String topik;
    private String tanggal;
    private String waktu;
}
