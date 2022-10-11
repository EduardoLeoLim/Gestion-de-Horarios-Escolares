package com.gamma.gestorhorariosescolares.alumno.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;

import java.util.List;

public interface AlumnoRepositorio {
    List<Alumno> buscar(Criterio criterio);

    int registrar(Alumno alumno);

    void actualizar(Alumno alumno);

    void eliminar(int idAlumno);
}
