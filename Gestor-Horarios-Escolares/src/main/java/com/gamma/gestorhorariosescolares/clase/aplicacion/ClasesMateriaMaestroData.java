package com.gamma.gestorhorariosescolares.clase.aplicacion;

import java.util.List;

public class ClasesMateriaMaestroData {

    private final List<ClaseMateriaMaestroData> clasesMaestro;

    public ClasesMateriaMaestroData(List<ClaseMateriaMaestroData> clases) {
        this.clasesMaestro = clases;
    }

    public List<ClaseMateriaMaestroData> clasesMaestro() {
        return clasesMaestro;
    }
}
