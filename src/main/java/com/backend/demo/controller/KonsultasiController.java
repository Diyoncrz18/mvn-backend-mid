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
import org.springframework.web.bind.annotation.RestController;

import com.backend.demo.dto.KonsultasiDto;
import com.backend.demo.dto.response.KonsultasiResponse;
import com.backend.demo.service.KonsultasiService;

@RestController
@RequestMapping("/api/konsultasi")
public class KonsultasiController {

    private final KonsultasiService konsultasiService;

    public KonsultasiController(KonsultasiService konsultasiService) {
        this.konsultasiService = konsultasiService;
    }

    @GetMapping
    public List<KonsultasiResponse> getAll() {
        return konsultasiService.getAll();
    }

    @GetMapping("/{id}")
    public KonsultasiResponse getById(@PathVariable Long id) {
        return konsultasiService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody KonsultasiDto dto) {
        KonsultasiResponse konsultasi = konsultasiService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Konsultasi berhasil dibuat", "data", konsultasi));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody KonsultasiDto dto) {
        KonsultasiResponse updated = konsultasiService.update(id, dto);
        return ResponseEntity.ok(Map.of("message", "Konsultasi berhasil diupdate", "data", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        KonsultasiResponse removed = konsultasiService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Konsultasi berhasil dihapus", "data", removed));
    }
}
