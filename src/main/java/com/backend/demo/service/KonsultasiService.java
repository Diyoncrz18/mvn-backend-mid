package com.backend.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.KonsultasiDto;
import com.backend.demo.model.Dosen;
import com.backend.demo.model.Konsultasi;
import com.backend.demo.model.Mahasiswa;

@Service
public class KonsultasiService {

    private final List<Konsultasi> daftarKonsultasi = new ArrayList<>();
    private final MahasiswaService mahasiswaService;
    private final DosenService dosenService;

    public KonsultasiService(MahasiswaService mahasiswaService, DosenService dosenService) {
        this.mahasiswaService = mahasiswaService;
        this.dosenService = dosenService;
    }

    public List<Konsultasi> getAll() {
        return daftarKonsultasi;
    }

    public Konsultasi create(KonsultasiDto dto) {
        Mahasiswa mhs = mahasiswaService.findById(dto.getMahasiswaId());
        if (mhs == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Mahasiswa dengan ID '" + dto.getMahasiswaId() + "' tidak ditemukan");
        }

        Dosen dsn = dosenService.findById(dto.getDosenId());
        if (dsn == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Dosen dengan ID '" + dto.getDosenId() + "' tidak ditemukan");
        }

        Konsultasi konsultasi = new Konsultasi(mhs, dsn, dto.getTopik(), dto.getTanggal(), dto.getWaktu());
        daftarKonsultasi.add(konsultasi);
        return konsultasi;
    }

    public Konsultasi delete(int index) {
        if (index < 1 || index > daftarKonsultasi.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Konsultasi dengan index " + index + " tidak ditemukan");
        }
        return daftarKonsultasi.remove(index - 1);
    }
}
