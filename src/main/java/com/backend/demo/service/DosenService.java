package com.backend.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.DosenDto;
import com.backend.demo.dto.response.DosenResponse;
import com.backend.demo.mapper.RelationMapper;
import com.backend.demo.model.Dosen;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.repository.DosenRepository;
import com.backend.demo.repository.KonsultasiRepository;

@Service
@Transactional(readOnly = true)
public class DosenService {

    private final DosenRepository dosenRepository;
    private final KonsultasiRepository konsultasiRepository;

    public DosenService(DosenRepository dosenRepository, KonsultasiRepository konsultasiRepository) {
        this.dosenRepository = dosenRepository;
        this.konsultasiRepository = konsultasiRepository;
    }

    public List<DosenResponse> getAll() {
        return dosenRepository.findAllByOrderByIdAsc().stream()
                .map(RelationMapper::toDosenResponse)
                .toList();
    }

    public DosenResponse getById(String id) {
        return RelationMapper.toDosenResponse(findEntityById(id));
    }

    @Transactional
    public DosenResponse create(DosenDto dto) {
        String id = requireText(dto.getId(), "ID dosen");
        if (dosenRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dosen dengan ID tersebut sudah ada");
        }

        Dosen dosen = new Dosen(id, requireText(dto.getNama(), "Nama dosen"), requireText(dto.getKeahlian(), "Keahlian"));
        return RelationMapper.toDosenResponse(dosenRepository.save(dosen));
    }

    @Transactional
    public DosenResponse update(String id, DosenDto dto) {
        Dosen dosen = findEntityById(id);
        dosen.setNama(requireText(dto.getNama(), "Nama dosen"));
        dosen.setKeahlian(requireText(dto.getKeahlian(), "Keahlian"));
        return RelationMapper.toDosenResponse(dosen);
    }

    @Transactional
    public DosenResponse delete(String id) {
        Dosen dosen = findEntityById(id);
        DosenResponse response = RelationMapper.toDosenResponse(dosen);

        for (Mahasiswa mahasiswa : List.copyOf(dosen.getMahasiswaDibimbing())) {
            mahasiswa.setDosenWali(null);
        }

        konsultasiRepository.deleteAll(konsultasiRepository.findAllByDosen_IdOrderByIdAsc(id));
        dosenRepository.delete(dosen);
        return response;
    }

    public Dosen findEntityById(String id) {
        return dosenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dosen dengan ID '" + id + "' tidak ditemukan"));
    }

    private String requireText(String value, String fieldName) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " wajib diisi");
        }
        return value.trim();
    }
}
