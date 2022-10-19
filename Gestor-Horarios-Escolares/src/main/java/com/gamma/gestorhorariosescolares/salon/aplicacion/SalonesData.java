package com.gamma.gestorhorariosescolares.salon.aplicacion;

import java.util.List;

public class SalonesData {
    private final List<SalonData> salones;

    public SalonesData(List<SalonData> salones) {
        this.salones = salones;
    }

    public List<SalonData> salones() {
        return salones;
    }
}
