package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoInscripcionData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaData;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;

public class EvaluacionInscripcionData {

    private final Integer id;
    private final String calificacion;
    private final String tipo;

    private final AlumnoInscripcionData alumno;
    private final ClaseMateriaData materia;

    public EvaluacionInscripcionData(Integer id, String calificacion, String tipo, AlumnoInscripcionData alumno, ClaseMateriaData materia) {
        this.id = id;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.alumno = alumno;
        this.materia = materia;
    }

    public static EvaluacionInscripcionData fromAggregate(Evaluacion evaluacion, AlumnoInscripcionData alumno, ClaseMateriaData materia){
        return new EvaluacionInscripcionData(evaluacion.id(), evaluacion.calificacion(), evaluacion.tipo(), alumno, materia);

    }

    public Integer id() {
        return id;
    }

    public String calificacion() {
        return calificacion;
    }

    public String tipo() {
        return tipo;
    }

    public AlumnoInscripcionData alumno() {
        return alumno;
    }

    public ClaseMateriaData materia() {
        return materia;
    }
}
