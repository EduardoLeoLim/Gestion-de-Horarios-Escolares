package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import java.util.List;

public class AlumnosData {
    private final List<AlumnoData> alumnos;

    public AlumnosData(List<AlumnoData> alumnos) {
        this.alumnos = alumnos;
    }

    public List<AlumnoData> alumnos() {
        return alumnos;
    }
}
