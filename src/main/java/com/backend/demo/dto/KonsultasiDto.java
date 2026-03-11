package com.backend.demo.dto;

import lombok.Data;

@Data
public class KonsultasiDto {
    private String mahasiswaId;
    private String dosenId;
    private String topik;
    private String tanggal;
    private String waktu;
}
