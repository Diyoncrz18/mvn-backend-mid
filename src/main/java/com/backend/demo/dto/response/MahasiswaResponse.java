package com.backend.demo.dto.response;

import java.util.List;

public record MahasiswaResponse(
        String id,
        String nama,
        String jurusan,
        DosenSummaryResponse dosenWali,
        List<BookSummaryResponse> books) {
}
