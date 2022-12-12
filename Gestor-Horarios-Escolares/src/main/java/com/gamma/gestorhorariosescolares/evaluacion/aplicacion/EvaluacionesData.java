package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import java.util.List;

public class EvaluacionesData {
    private final List<EvaluacionData> evaluaciones;

    public EvaluacionesData(List<EvaluacionData> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<EvaluacionData> evaluaciones() {
        return evaluaciones;
    }
}
