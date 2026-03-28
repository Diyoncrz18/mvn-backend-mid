package com.backend.demo.dto.response;

import java.util.List;

public record DosenResponse(
        String id,
        String nama,
        String keahlian,
        List<MahasiswaSummaryResponse> mahasiswaBimbingan) {
}
