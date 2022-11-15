package com.gamma.gestorhorariosescolares.clase.aplicacion;

import java.util.List;

public class ClasesData {

    private final List<ClaseData> clases;

    public ClasesData(List<ClaseData> clases) {
        this.clases = clases;
    }

    public List<ClaseData> clases() {
        return clases;
    }
}
