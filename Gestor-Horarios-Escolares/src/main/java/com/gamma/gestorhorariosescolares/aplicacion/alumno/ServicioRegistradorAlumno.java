package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.dominio.alumno.AlumnoRepositorio;

public class ServicioRegistradorAlumno implements RegistradorAlumno {
    private final AlumnoRepositorio repositorio;

    public ServicioRegistradorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public int registrar() {
        return -1;
    }
}
