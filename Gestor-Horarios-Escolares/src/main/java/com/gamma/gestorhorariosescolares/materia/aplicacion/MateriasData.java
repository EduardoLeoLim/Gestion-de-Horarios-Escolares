package com.gamma.gestorhorariosescolares.materia.aplicacion;

import java.util.List;

public class MateriasData {
    private final List<MateriaData> materias;

    public MateriasData(List<MateriaData> materias) {
        this.materias = materias;
    }

    public List<MateriaData> materias() {
        return materias;
    }
}
