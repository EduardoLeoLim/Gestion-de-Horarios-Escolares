package com.gamma.gestorhorariosescolares.clase.aplicacion;

import java.util.List;

public class ClasesAlumnoData {

    private final List<ClaseAlumnoData> clasesAlumno;

    public ClasesAlumnoData(List<ClaseAlumnoData> clasesAlumno) {
        this.clasesAlumno = clasesAlumno;
    }

    public List<ClaseAlumnoData> clasesAlumno() {
        return clasesAlumno;
    }


}
