package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.dominio.alumno.AlumnoRepositorio;

public class RegistradorAlumno implements ServicioRegistradorAlumno {
    private final AlumnoRepositorio repositorio;

    public RegistradorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public int registrar() {
        return -1;
    }
}
