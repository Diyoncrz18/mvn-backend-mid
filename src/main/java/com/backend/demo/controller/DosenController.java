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

import com.backend.demo.dto.DosenDto;
import com.backend.demo.model.Dosen;
import com.backend.demo.service.DosenService;

@RestController
@RequestMapping("/api/dosen")
public class DosenController {

    private final DosenService dosenService;

    public DosenController(DosenService dosenService) {
        this.dosenService = dosenService;
    }

    @GetMapping
    public List<Dosen> getAll() {
        return dosenService.getAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody DosenDto dto) {
        Dosen dosen = dosenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Dosen berhasil ditambahkan", "data", dosen));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String id) {
        Dosen removed = dosenService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Dosen berhasil dihapus", "data", removed));
    }
}
