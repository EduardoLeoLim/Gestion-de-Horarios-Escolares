package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.dominio.alumno.Alumno;

import java.util.List;

public interface ServicioBuscadorAlumno {
    ServicioBuscadorAlumno filtarNombre(String nombre);

    ServicioBuscadorAlumno filtarApellidoPaterno(String apellidoPaterno);

    ServicioBuscadorAlumno filtarApellidoMaterno(String apellidoMaterno);

    ServicioBuscadorAlumno filtarCURP(String curp);

    ServicioBuscadorAlumno filtarMatricula(String matricula);

    List<Alumno> buscar();

}
