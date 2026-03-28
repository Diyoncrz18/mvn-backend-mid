package com.backend.demo.dto;

import java.util.Set;

import lombok.Data;

@Data
public class MahasiswaDto {
    private String id;
    private String nama;
    private String jurusan;
    private String dosenId;
    private Set<Long> bookIds;
}
