package com.backend.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.demo.model.Dosen;

public interface DosenRepository extends JpaRepository<Dosen, String> {
    List<Dosen> findAllByOrderByIdAsc();
}
