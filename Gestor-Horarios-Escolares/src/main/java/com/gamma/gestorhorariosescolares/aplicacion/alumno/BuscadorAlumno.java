package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.dominio.alumno.Alumno;

import java.util.List;

public interface BuscadorAlumno {
    BuscadorAlumno filtarNombre(String nombre);

    BuscadorAlumno filtarApellidoPaterno(String apellidoPaterno);

    BuscadorAlumno filtarApellidoMaterno(String apellidoMaterno);

    BuscadorAlumno filtarCURP(String curp);

    BuscadorAlumno filtarMatricula(String matricula);

    List<Alumno> buscar();

}
