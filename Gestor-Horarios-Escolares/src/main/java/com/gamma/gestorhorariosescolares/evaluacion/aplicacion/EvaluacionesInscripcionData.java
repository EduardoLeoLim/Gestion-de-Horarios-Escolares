package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import java.util.List;

public class EvaluacionesInscripcionData {
    List<EvaluacionInscripcionData> evaluaciones;

    public EvaluacionesInscripcionData(List<EvaluacionInscripcionData> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<EvaluacionInscripcionData> evaluaciones() {
        return evaluaciones;
    }
}
