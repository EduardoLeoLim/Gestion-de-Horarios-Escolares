package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;
import java.util.stream.Collectors;

public class PlanesEstudioData {

    private final List<PlanEstudioData> planesEstudio;

    public PlanesEstudioData(List<PlanEstudio> planesEstudio) {
        this.planesEstudio = planesEstudio.stream().map(PlanEstudioData::fromAggregate).collect(Collectors.toList());
    }

    public List<PlanEstudioData> planesEstudio() {
        return planesEstudio;
    }

}