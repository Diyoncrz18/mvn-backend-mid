package com.backend.demo.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.demo.dto.BookDto;
import com.backend.demo.dto.DosenDto;
import com.backend.demo.dto.KonsultasiDto;
import com.backend.demo.dto.MahasiswaDto;
import com.backend.demo.service.BookService;
import com.backend.demo.service.DosenService;
import com.backend.demo.service.KonsultasiService;
import com.backend.demo.service.MahasiswaService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            DosenService dosenService,
            MahasiswaService mahasiswaService,
            BookService bookService,
            KonsultasiService konsultasiService) {
        return args -> {
            dosenService.create(dosen("D01", "Stenly Pungus", "Software Engineer"));
            dosenService.create(dosen("D02", "Debby Sondakh", "Research Method"));
            dosenService.create(dosen("D03", "Semmy Taju", "Artificial Intelligence"));
            dosenService.create(dosen("D04", "Green Sandag", "Expert System"));

            bookService.create(book(1L, "Clean Code", "Robert C. Martin", "ISBN-001"));
            bookService.create(book(2L, "Spring in Action", "Craig Walls", "ISBN-002"));
            bookService.create(book(3L, "Database System Concepts", "Silberschatz", "ISBN-003"));

            mahasiswaService.create(mahasiswa("M01", "Dion Kobi", "Teknik Informatika", "D01", Set.of(1L, 2L)));
            mahasiswaService.create(mahasiswa("M02", "Clio Marco Mataheru", "Teknik Informatika", "D01", Set.of(2L)));
            mahasiswaService.create(mahasiswa("M03", "George Stivo Kaunang", "Sistem Informasi", "D03", Set.of(1L, 3L)));
            mahasiswaService.create(mahasiswa("M04", "Geovani Waladow", "Teknik Informatika", "D04", Set.of(3L)));

            konsultasiService.create(konsultasi("M01", "D01", "JPA One To Many", "2026-03-28", "09:00"));
            konsultasiService.create(konsultasi("M02", "D01", "Spring Boot Repository", "2026-03-28", "10:00"));
            konsultasiService.create(konsultasi("M03", "D03", "Many To Many Mapping", "2026-03-28", "13:30"));

            System.out.println("========================================");
            System.out.println("  CONTOH RELASI DATABASE DENGAN JPA");
            System.out.println("========================================");
            System.out.println("  Data awal berhasil dimuat:");
            System.out.println("  - " + dosenService.getAll().size() + " dosen");
            System.out.println("  - " + mahasiswaService.getAll().size() + " mahasiswa");
            System.out.println("  - " + bookService.getAll().size() + " book");
            System.out.println("  - " + konsultasiService.getAll().size() + " konsultasi");
            System.out.println();
            System.out.println("  Contoh relasi:");
            System.out.println("  - One To Many : Dosen -> Mahasiswa");
            System.out.println("  - Many To Many: Mahasiswa <-> Book");
            System.out.println();
            System.out.println("  Endpoint utama:");
            System.out.println("  GET    /api/dosen");
            System.out.println("  GET    /api/mahasiswa");
            System.out.println("  GET    /api/books");
            System.out.println("  GET    /api/konsultasi");
            System.out.println("  H2 Console: /h2-console");
            System.out.println("========================================");
        };
    }

    private DosenDto dosen(String id, String nama, String keahlian) {
        DosenDto dto = new DosenDto();
        dto.setId(id);
        dto.setNama(nama);
        dto.setKeahlian(keahlian);
        return dto;
    }

    private BookDto book(Long id, String title, String author, String isbn) {
        BookDto dto = new BookDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setIsbn(isbn);
        return dto;
    }

    private MahasiswaDto mahasiswa(String id, String nama, String jurusan, String dosenId, Set<Long> bookIds) {
        MahasiswaDto dto = new MahasiswaDto();
        dto.setId(id);
        dto.setNama(nama);
        dto.setJurusan(jurusan);
        dto.setDosenId(dosenId);
        dto.setBookIds(bookIds);
        return dto;
    }

    private KonsultasiDto konsultasi(String mahasiswaId, String dosenId, String topik, String tanggal, String waktu) {
        KonsultasiDto dto = new KonsultasiDto();
        dto.setMahasiswaId(mahasiswaId);
        dto.setDosenId(dosenId);
        dto.setTopik(topik);
        dto.setTanggal(tanggal);
        dto.setWaktu(waktu);
        return dto;
    }
}
