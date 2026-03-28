package com.backend.demo.dto.response;

import java.util.List;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        List<MahasiswaSummaryResponse> mahasiswa) {
}
