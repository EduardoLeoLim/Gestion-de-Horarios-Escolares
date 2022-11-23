package com.gamma.gestorhorariosescolares.inscripcion.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface InscripcionRepositorio {
    List<Inscripcion> buscar(Criteria criterio);

    int registrar(Inscripcion inscripcion);

    void actualizar(Inscripcion inscripcion);
}
