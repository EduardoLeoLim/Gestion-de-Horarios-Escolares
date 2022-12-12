package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMaestroData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaData;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaClaseData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;

public class EvaluacionData {
    private final Integer id;
    private final String evaluacion;
    private final ClaseMaestroData maestro;
    private final ClaseMateriaData materia;


    public EvaluacionData(Integer id, String evaluacion, ClaseMaestroData maestro, ClaseMateriaData materia) {
        this.id = id;
        this.evaluacion = evaluacion;
        this.maestro = maestro;
        this.materia = materia;
    }

    public static EvaluacionData fromAgggregate(Evaluacion evaluacion, ClaseMaestroData maestro, ClaseMateriaData materia){
        return new EvaluacionData(evaluacion.id(), evaluacion.calificacion(), maestro, materia);

    }

    public Integer id() {
        return id;
    }

    public String evaluacion() {
        return evaluacion;
    }

    public ClaseMaestroData maestro() {
        return maestro;
    }

    public ClaseMateriaData materia() {
        return materia;
    }
}
