package com.backend.demo.dto.response;

public record KonsultasiResponse(
        Long id,
        String topik,
        String tanggal,
        String waktu,
        MahasiswaSummaryResponse mahasiswa,
        DosenSummaryResponse dosen) {
}
