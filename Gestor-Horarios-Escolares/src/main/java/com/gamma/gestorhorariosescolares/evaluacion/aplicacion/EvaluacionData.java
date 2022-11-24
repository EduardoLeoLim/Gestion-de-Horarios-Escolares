package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;

public class EvaluacionData {
    private final Integer id;
    private final String evaluacion;
    private final AlumnoData alumno;
    private final MateriaData materia;

    public EvaluacionData(Integer id, String evaluacion, AlumnoData alumno, MateriaData materia) {
        this.id = id;
        this.evaluacion = evaluacion;
        this.alumno = alumno;
        this.materia = materia;
    }
}
