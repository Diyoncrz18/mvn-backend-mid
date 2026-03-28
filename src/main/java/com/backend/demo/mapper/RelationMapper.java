package com.backend.demo.mapper;

import java.util.Comparator;
import java.util.List;

import com.backend.demo.dto.response.BookResponse;
import com.backend.demo.dto.response.BookSummaryResponse;
import com.backend.demo.dto.response.DosenResponse;
import com.backend.demo.dto.response.DosenSummaryResponse;
import com.backend.demo.dto.response.KonsultasiResponse;
import com.backend.demo.dto.response.MahasiswaResponse;
import com.backend.demo.dto.response.MahasiswaSummaryResponse;
import com.backend.demo.model.Book;
import com.backend.demo.model.Dosen;
import com.backend.demo.model.Konsultasi;
import com.backend.demo.model.Mahasiswa;

public final class RelationMapper {

    private RelationMapper() {
    }

    public static DosenResponse toDosenResponse(Dosen dosen) {
        List<MahasiswaSummaryResponse> mahasiswaBimbingan = dosen.getMahasiswaDibimbing().stream()
                .sorted(Comparator.comparing(Mahasiswa::getId))
                .map(RelationMapper::toMahasiswaSummary)
                .toList();

        return new DosenResponse(dosen.getId(), dosen.getNama(), dosen.getKeahlian(), mahasiswaBimbingan);
    }

    public static MahasiswaResponse toMahasiswaResponse(Mahasiswa mahasiswa) {
        List<BookSummaryResponse> books = mahasiswa.getBooks().stream()
                .sorted(Comparator.comparing(Book::getId))
                .map(RelationMapper::toBookSummary)
                .toList();

        return new MahasiswaResponse(
                mahasiswa.getId(),
                mahasiswa.getNama(),
                mahasiswa.getJurusan(),
                toDosenSummary(mahasiswa.getDosenWali()),
                books);
    }

    public static BookResponse toBookResponse(Book book) {
        List<MahasiswaSummaryResponse> mahasiswa = book.getMahasiswa().stream()
                .sorted(Comparator.comparing(Mahasiswa::getId))
                .map(RelationMapper::toMahasiswaSummary)
                .toList();

        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), mahasiswa);
    }

    public static KonsultasiResponse toKonsultasiResponse(Konsultasi konsultasi) {
        return new KonsultasiResponse(
                konsultasi.getId(),
                konsultasi.getTopik(),
                konsultasi.getTanggal(),
                konsultasi.getWaktu(),
                toMahasiswaSummary(konsultasi.getMahasiswa()),
                toDosenSummary(konsultasi.getDosen()));
    }

    public static DosenSummaryResponse toDosenSummary(Dosen dosen) {
        if (dosen == null) {
            return null;
        }
        return new DosenSummaryResponse(dosen.getId(), dosen.getNama());
    }

    public static MahasiswaSummaryResponse toMahasiswaSummary(Mahasiswa mahasiswa) {
        if (mahasiswa == null) {
            return null;
        }
        return new MahasiswaSummaryResponse(mahasiswa.getId(), mahasiswa.getNama());
    }

    public static BookSummaryResponse toBookSummary(Book book) {
        if (book == null) {
            return null;
        }
        return new BookSummaryResponse(book.getId(), book.getTitle());
    }
}
