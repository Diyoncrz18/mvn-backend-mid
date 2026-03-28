package com.backend.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.demo.model.Konsultasi;

public interface KonsultasiRepository extends JpaRepository<Konsultasi, Long> {
    List<Konsultasi> findAllByOrderByIdAsc();

    List<Konsultasi> findAllByDosen_IdOrderByIdAsc(String dosenId);

    List<Konsultasi> findAllByMahasiswa_IdOrderByIdAsc(String mahasiswaId);
}
