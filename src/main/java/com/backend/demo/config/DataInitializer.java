package com.backend.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.demo.model.Dosen;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.service.DosenService;
import com.backend.demo.service.MahasiswaService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(DosenService dosenService, MahasiswaService mahasiswaService) {
        return args -> {
            // Data awal dosen
            dosenService.addInitialData(List.of(
                    new Dosen("D01", "Stenly Pungus", "Software Engineer"),
                    new Dosen("D02", "Debby Sondakh", "Research Method"),
                    new Dosen("D03", "Semmy Taju", "Artificial Intelligence"),
                    new Dosen("D04", "Green Sandag", "Expert System"),
                    new Dosen("D05", "Green Mandias", "DBMS"),
                    new Dosen("D06", "Andrew Liem", "Computer Network"),
                    new Dosen("D07", "Oktoverano Lengkong", "UI/UX")
            ));

            // Data awal mahasiswa
            mahasiswaService.addInitialData(List.of(
                    new Mahasiswa("M01", "Dion Kobi", "Teknik Informatika"),
                    new Mahasiswa("M02", "Clio Marco Mataheru", "Teknik Informatika"),
                    new Mahasiswa("M03", "George Stivo Kaunang", "Teknik Informatika"),
                    new Mahasiswa("M04", "Geovani Waladow", "Teknik Informatika")
            ));

            System.out.println("========================================");
            System.out.println("   SISTEM KONSULTASI MAHASISWA - DOSEN  ");
            System.out.println("       Spring Boot REST API (OOP)       ");
            System.out.println("========================================");
            System.out.println("  Data awal berhasil dimuat:");
            System.out.println("  - " + dosenService.getAll().size() + " dosen");
            System.out.println("  - " + mahasiswaService.getAll().size() + " mahasiswa");
            System.out.println();
            System.out.println("  Endpoints:");
            System.out.println("  GET    /api/dosen              - Lihat semua dosen");
            System.out.println("  POST   /api/dosen              - Tambah dosen");
            System.out.println("  DELETE /api/dosen?id=D01       - Hapus dosen by ID");
            System.out.println("  GET    /api/mahasiswa           - Lihat semua mahasiswa");
            System.out.println("  POST   /api/mahasiswa           - Tambah mahasiswa");
            System.out.println("  DELETE /api/mahasiswa?id=M01    - Hapus mahasiswa by ID");
            System.out.println("  GET    /api/konsultasi          - Lihat semua konsultasi");
            System.out.println("  POST   /api/konsultasi          - Buat konsultasi");
            System.out.println("  DELETE /api/konsultasi?index=1  - Hapus konsultasi by index");
            System.out.println("========================================");
        };
    }
}
