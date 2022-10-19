package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;
import java.util.stream.Collectors;

public class MateriasData {
    private final List<MateriaData> materias;

    public MateriasData(List<Materia> materias) {
        if (materias == null)
            throw new NullPointerException();

        this.materias = materias.stream().map((MateriaData::fromAggregate)).collect(Collectors.toList());
    }

    public List<MateriaData> materias() {
        return materias;
    }
}
