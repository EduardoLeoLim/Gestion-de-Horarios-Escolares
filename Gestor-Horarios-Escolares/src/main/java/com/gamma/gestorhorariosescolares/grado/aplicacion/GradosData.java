package com.gamma.gestorhorariosescolares.grado.aplicacion;

import com.gamma.gestorhorariosescolares.grado.dominio.Grado;

import java.util.List;
import java.util.stream.Collectors;

public class GradosData {

    private final List<GradoData> grados;

    public GradosData(List<Grado> grados) {
        this.grados = grados.stream().map(GradoData::fromAggregate).collect(Collectors.toList());
    }

    public List<GradoData> grados() {
        return grados;
    }

}