package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import java.util.List;

public class PlanesEstudioData {

    private final List<PlanEstudioData> planesEstudio;

    public PlanesEstudioData(List<PlanEstudioData> planesEstudio) {
        this.planesEstudio = planesEstudio;
    }

    public List<PlanEstudioData> planesEstudio() {
        return planesEstudio;
    }
}
