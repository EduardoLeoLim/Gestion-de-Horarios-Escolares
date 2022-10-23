package com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;

public class ActualizadorAlumno implements ServicioActualizadorAlumno {

    private final AlumnoRepositorio repositorio;

    public ActualizadorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Alumno alumno) {
        //validar formato de datos

        repositorio.actualizar(alumno);
    }

}
