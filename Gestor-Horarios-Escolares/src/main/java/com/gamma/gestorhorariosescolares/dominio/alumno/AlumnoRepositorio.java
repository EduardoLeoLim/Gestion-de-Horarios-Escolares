package com.gamma.gestorhorariosescolares.dominio.alumno;

import com.gamma.gestorhorariosescolares.dominio.compartido.Criterio;

import java.util.List;

public interface AlumnoRepositorio {
    List<Alumno> buscar(Criterio criterio);

    int registrar(Alumno alumno);

    void actualizar(Alumno alumno);

    void eliminar(int idAlumno);
}
