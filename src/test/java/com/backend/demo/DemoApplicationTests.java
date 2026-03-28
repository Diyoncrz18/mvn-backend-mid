package com.backend.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.demo.model.Dosen;
import com.backend.demo.model.Mahasiswa;
import com.backend.demo.repository.DosenRepository;
import com.backend.demo.repository.MahasiswaRepository;

@SpringBootTest
@Transactional
class DemoApplicationTests {

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Test
    void contextLoads() {
        assertThat(dosenRepository.count()).isGreaterThan(0);
        assertThat(mahasiswaRepository.count()).isGreaterThan(0);
    }

    @Test
    void shouldLoadOneToManyRelation() {
        Dosen dosen = dosenRepository.findById("D01").orElseThrow();

        assertThat(dosen.getMahasiswaDibimbing())
                .extracting(Mahasiswa::getId)
                .containsExactlyInAnyOrder("M01", "M02");
    }

    @Test
    void shouldLoadManyToManyRelation() {
        Mahasiswa mahasiswa = mahasiswaRepository.findById("M01").orElseThrow();

        assertThat(mahasiswa.getBooks())
                .extracting(book -> book.getId())
                .containsExactlyInAnyOrder(1L, 2L);
    }
}
