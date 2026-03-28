package com.backend.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.demo.dto.MahasiswaDto;
import com.backend.demo.dto.response.MahasiswaResponse;
import com.backend.demo.service.MahasiswaService;

@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {

    private final MahasiswaService mahasiswaService;

    public MahasiswaController(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }

    @GetMapping
    public List<MahasiswaResponse> getAll() {
        return mahasiswaService.getAll();
    }

    @GetMapping("/{id}")
    public MahasiswaResponse getById(@PathVariable String id) {
        return mahasiswaService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody MahasiswaDto dto) {
        MahasiswaResponse mahasiswa = mahasiswaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Mahasiswa berhasil ditambahkan", "data", mahasiswa));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@RequestParam String id, @RequestBody MahasiswaDto dto) {
        MahasiswaResponse updated = mahasiswaService.update(id, dto);
        return ResponseEntity.ok(Map.of("message", "Mahasiswa berhasil diupdate", "data", updated));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String id) {
        MahasiswaResponse removed = mahasiswaService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Mahasiswa berhasil dihapus", "data", removed));
    }
}
