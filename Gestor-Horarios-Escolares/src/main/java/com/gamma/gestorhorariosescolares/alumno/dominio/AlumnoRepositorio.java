package com.gamma.gestorhorariosescolares.alumno.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface AlumnoRepositorio {
    List<Alumno> buscar(Criteria criterio);

    int registrar(Alumno alumno);

    void actualizar(Alumno alumno);
}
