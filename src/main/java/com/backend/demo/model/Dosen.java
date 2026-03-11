package com.backend.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dosen extends Person {
    private String keahlian;

    public Dosen(String id, String nama, String keahlian) {
        super(id, nama);
        this.keahlian = keahlian;
    }
}
