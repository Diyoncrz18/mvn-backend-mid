package com.backend.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.MahasiswaDto;
import com.backend.demo.model.Mahasiswa;

@Service
public class MahasiswaService {

    private final List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

    public List<Mahasiswa> getAll() {
        return daftarMahasiswa;
    }

    public Mahasiswa create(MahasiswaDto dto) {
        for (Mahasiswa m : daftarMahasiswa) {
            if (m.getId().equals(dto.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mahasiswa dengan ID tersebut sudah ada");
            }
        }
        Mahasiswa mahasiswa = new Mahasiswa(dto.getId(), dto.getNama(), dto.getJurusan());
        daftarMahasiswa.add(mahasiswa);
        return mahasiswa;
    }

    public Mahasiswa delete(String id) {
        for (int i = 0; i < daftarMahasiswa.size(); i++) {
            if (daftarMahasiswa.get(i).getId().equals(id)) {
                return daftarMahasiswa.remove(i);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mahasiswa dengan ID '" + id + "' tidak ditemukan");
    }

    public Mahasiswa findById(String id) {
        for (Mahasiswa m : daftarMahasiswa) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public void addInitialData(List<Mahasiswa> data) {
        daftarMahasiswa.addAll(data);
    }
}
