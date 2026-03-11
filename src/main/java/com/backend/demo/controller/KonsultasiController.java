package com.backend.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.backend.demo.dto.KonsultasiDto;
import com.backend.demo.model.Konsultasi;
import com.backend.demo.service.KonsultasiService;

@RestController
@RequestMapping("/api/konsultasi")
public class KonsultasiController {

    private final KonsultasiService konsultasiService;

    public KonsultasiController(KonsultasiService konsultasiService) {
        this.konsultasiService = konsultasiService;
    }

    @GetMapping
    public List<Konsultasi> getAll() {
        return konsultasiService.getAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody KonsultasiDto dto) {
        Konsultasi konsultasi = konsultasiService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Konsultasi berhasil dibuat", "data", konsultasi));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(@RequestParam int index) {
        Konsultasi removed = konsultasiService.delete(index);
        return ResponseEntity.ok(Map.of("message", "Konsultasi berhasil dihapus", "data", removed));
    }
}
