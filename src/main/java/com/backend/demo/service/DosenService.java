package com.backend.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.DosenDto;
import com.backend.demo.model.Dosen;

@Service
public class DosenService {

    private final List<Dosen> daftarDosen = new ArrayList<>();

    public List<Dosen> getAll() {
        return daftarDosen;
    }

    public Dosen create(DosenDto dto) {
        for (Dosen d : daftarDosen) {
            if (d.getId().equals(dto.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Dosen dengan ID tersebut sudah ada");
            }
        }
        Dosen dosen = new Dosen(dto.getId(), dto.getNama(), dto.getKeahlian());
        daftarDosen.add(dosen);
        return dosen;
    }

    public Dosen delete(String id) {
        for (int i = 0; i < daftarDosen.size(); i++) {
            if (daftarDosen.get(i).getId().equals(id)) {
                return daftarDosen.remove(i);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dosen dengan ID '" + id + "' tidak ditemukan");
    }

    public Dosen findById(String id) {
        for (Dosen d : daftarDosen) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public void addInitialData(List<Dosen> data) {
        daftarDosen.addAll(data);
    }
}
