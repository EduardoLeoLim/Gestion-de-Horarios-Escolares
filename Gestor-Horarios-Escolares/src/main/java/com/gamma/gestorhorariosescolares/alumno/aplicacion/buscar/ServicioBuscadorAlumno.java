package com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;

import java.util.List;

public interface ServicioBuscadorAlumno {
    ServicioBuscadorAlumno filtarNombre(String nombre);

    ServicioBuscadorAlumno filtarApellidoPaterno(String apellidoPaterno);

    ServicioBuscadorAlumno filtarApellidoMaterno(String apellidoMaterno);

    ServicioBuscadorAlumno filtarCURP(String curp);

    ServicioBuscadorAlumno filtarMatricula(String matricula);

    List<Alumno> buscar();

}
