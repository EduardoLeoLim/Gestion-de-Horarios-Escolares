package com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;

public class RegistradorAlumno implements ServicioRegistradorAlumno {
    private final AlumnoRepositorio repositorio;

    public RegistradorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public int registrar(String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        //Validar formato de datos
        //Si son datos inválidos, lanzar excepción

        Alumno nuevoAlumno = new Alumno(matricula, curp, nombre, apellidoPaterno, apellidoMaterno, idUsuario);

        return repositorio.registrar(nuevoAlumno);
    }
}
