package com.backend.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.KonsultasiDto;
import com.backend.demo.dto.response.KonsultasiResponse;
import com.backend.demo.mapper.RelationMapper;
import com.backend.demo.model.Dosen;
import com.backend.demo.model.Konsultasi;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.repository.DosenRepository;
import com.backend.demo.repository.KonsultasiRepository;
import com.backend.demo.repository.MahasiswaRepository;

@Service
@Transactional(readOnly = true)
public class KonsultasiService {

    private final KonsultasiRepository konsultasiRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;

    public KonsultasiService(
            KonsultasiRepository konsultasiRepository,
            MahasiswaRepository mahasiswaRepository,
            DosenRepository dosenRepository) {
        this.konsultasiRepository = konsultasiRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
    }

    public List<KonsultasiResponse> getAll() {
        return konsultasiRepository.findAllByOrderByIdAsc().stream()
                .map(RelationMapper::toKonsultasiResponse)
                .toList();
    }

    public KonsultasiResponse getById(Long id) {
        return RelationMapper.toKonsultasiResponse(findEntityById(id));
    }

    @Transactional
    public KonsultasiResponse create(KonsultasiDto dto) {
        Konsultasi konsultasi = new Konsultasi(
                resolveMahasiswa(dto.getMahasiswaId()),
                resolveDosen(dto.getDosenId()),
                requireText(dto.getTopik(), "Topik konsultasi"),
                requireText(dto.getTanggal(), "Tanggal konsultasi"),
                requireText(dto.getWaktu(), "Waktu konsultasi"));
        return RelationMapper.toKonsultasiResponse(konsultasiRepository.save(konsultasi));
    }

    @Transactional
    public KonsultasiResponse update(Long id, KonsultasiDto dto) {
        Konsultasi konsultasi = findEntityById(id);
        konsultasi.setMahasiswa(resolveMahasiswa(dto.getMahasiswaId()));
        konsultasi.setDosen(resolveDosen(dto.getDosenId()));
        konsultasi.setTopik(requireText(dto.getTopik(), "Topik konsultasi"));
        konsultasi.setTanggal(requireText(dto.getTanggal(), "Tanggal konsultasi"));
        konsultasi.setWaktu(requireText(dto.getWaktu(), "Waktu konsultasi"));
        return RelationMapper.toKonsultasiResponse(konsultasi);
    }

    @Transactional
    public KonsultasiResponse delete(Long id) {
        Konsultasi konsultasi = findEntityById(id);
        KonsultasiResponse response = RelationMapper.toKonsultasiResponse(konsultasi);
        konsultasiRepository.delete(konsultasi);
        return response;
    }

    private Konsultasi findEntityById(Long id) {
        return konsultasiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Konsultasi dengan ID '" + id + "' tidak ditemukan"));
    }

    private Mahasiswa resolveMahasiswa(String mahasiswaId) {
        String normalizedId = requireText(mahasiswaId, "ID mahasiswa");
        return mahasiswaRepository.findById(normalizedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Mahasiswa dengan ID '" + normalizedId + "' tidak ditemukan"));
    }

    private Dosen resolveDosen(String dosenId) {
        String normalizedId = requireText(dosenId, "ID dosen");
        return dosenRepository.findById(normalizedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dosen dengan ID '" + normalizedId + "' tidak ditemukan"));
    }

    private String requireText(String value, String fieldName) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " wajib diisi");
        }
        return value.trim();
    }
}
