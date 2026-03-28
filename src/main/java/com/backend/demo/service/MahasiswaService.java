package com.backend.demo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.demo.dto.MahasiswaDto;
import com.backend.demo.dto.response.MahasiswaResponse;
import com.backend.demo.mapper.RelationMapper;
import com.backend.demo.model.Book;
import com.backend.demo.model.Dosen;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.repository.BookRepository;
import com.backend.demo.repository.DosenRepository;
import com.backend.demo.repository.KonsultasiRepository;
import com.backend.demo.repository.MahasiswaRepository;

@Service
@Transactional(readOnly = true)
public class MahasiswaService {

    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;
    private final BookRepository bookRepository;
    private final KonsultasiRepository konsultasiRepository;

    public MahasiswaService(
            MahasiswaRepository mahasiswaRepository,
            DosenRepository dosenRepository,
            BookRepository bookRepository,
            KonsultasiRepository konsultasiRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
        this.bookRepository = bookRepository;
        this.konsultasiRepository = konsultasiRepository;
    }

    public List<MahasiswaResponse> getAll() {
        return mahasiswaRepository.findAllByOrderByIdAsc().stream()
                .map(RelationMapper::toMahasiswaResponse)
                .toList();
    }

    public MahasiswaResponse getById(String id) {
        return RelationMapper.toMahasiswaResponse(findEntityById(id));
    }

    @Transactional
    public MahasiswaResponse create(MahasiswaDto dto) {
        String id = requireText(dto.getId(), "ID mahasiswa");
        if (mahasiswaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mahasiswa dengan ID tersebut sudah ada");
        }

        Mahasiswa mahasiswa = new Mahasiswa(
                id,
                requireText(dto.getNama(), "Nama mahasiswa"),
                requireText(dto.getJurusan(), "Jurusan"));
        mahasiswa.setDosenWali(resolveDosen(dto.getDosenId()));
        replaceBooks(mahasiswa, dto.getBookIds());
        return RelationMapper.toMahasiswaResponse(mahasiswaRepository.save(mahasiswa));
    }

    @Transactional
    public MahasiswaResponse update(String id, MahasiswaDto dto) {
        Mahasiswa mahasiswa = findEntityById(id);
        mahasiswa.setNama(requireText(dto.getNama(), "Nama mahasiswa"));
        mahasiswa.setJurusan(requireText(dto.getJurusan(), "Jurusan"));
        mahasiswa.setDosenWali(resolveDosen(dto.getDosenId()));
        replaceBooks(mahasiswa, dto.getBookIds());
        return RelationMapper.toMahasiswaResponse(mahasiswa);
    }

    @Transactional
    public MahasiswaResponse delete(String id) {
        Mahasiswa mahasiswa = findEntityById(id);
        MahasiswaResponse response = RelationMapper.toMahasiswaResponse(mahasiswa);

        konsultasiRepository.deleteAll(konsultasiRepository.findAllByMahasiswa_IdOrderByIdAsc(id));
        mahasiswa.getBooks().clear();
        mahasiswa.setDosenWali(null);
        mahasiswaRepository.delete(mahasiswa);
        return response;
    }

    public Mahasiswa findEntityById(String id) {
        return mahasiswaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Mahasiswa dengan ID '" + id + "' tidak ditemukan"));
    }

    private void replaceBooks(Mahasiswa mahasiswa, Set<Long> bookIds) {
        mahasiswa.getBooks().clear();
        mahasiswa.getBooks().addAll(resolveBooks(bookIds));
    }

    private Dosen resolveDosen(String dosenId) {
        if (Objects.isNull(dosenId) || dosenId.isBlank()) {
            return null;
        }
        return dosenRepository.findById(dosenId.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dosen dengan ID '" + dosenId + "' tidak ditemukan"));
    }

    private Set<Book> resolveBooks(Set<Long> bookIds) {
        if (bookIds == null || bookIds.isEmpty()) {
            return new LinkedHashSet<>();
        }

        List<Long> normalizedIds = bookIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        List<Book> books = bookRepository.findAllById(normalizedIds);

        if (books.size() != normalizedIds.size()) {
            Set<Long> foundIds = books.stream()
                    .map(Book::getId)
                    .collect(LinkedHashSet::new, Set::add, Set::addAll);
            Long missingId = normalizedIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .orElse(null);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book dengan ID '" + missingId + "' tidak ditemukan");
        }

        return new LinkedHashSet<>(books);
    }

    private String requireText(String value, String fieldName) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " wajib diisi");
        }
        return value.trim();
    }
}
