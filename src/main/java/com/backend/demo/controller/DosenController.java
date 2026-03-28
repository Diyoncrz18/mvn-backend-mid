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

import com.backend.demo.dto.DosenDto;
import com.backend.demo.dto.response.DosenResponse;
import com.backend.demo.service.DosenService;

@RestController
@RequestMapping("/api/dosen")
public class DosenController {

    private final DosenService dosenService;

    public DosenController(DosenService dosenService) {
        this.dosenService = dosenService;
    }

    @GetMapping
    public List<DosenResponse> getAll() {
        return dosenService.getAll();
    }

    @GetMapping("/{id}")
    public DosenResponse getById(@PathVariable String id) {
        return dosenService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody DosenDto dto) {
        DosenResponse dosen = dosenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Dosen berhasil ditambahkan", "data", dosen));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@RequestParam String id, @RequestBody DosenDto dto) {
        DosenResponse updated = dosenService.update(id, dto);
        return ResponseEntity.ok(Map.of("message", "Dosen berhasil diupdate", "data", updated));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String id) {
        DosenResponse removed = dosenService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Dosen berhasil dihapus", "data", removed));
    }
}
