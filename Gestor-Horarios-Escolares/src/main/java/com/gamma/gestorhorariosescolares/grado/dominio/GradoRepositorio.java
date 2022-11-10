package com.gamma.gestorhorariosescolares.grado.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface GradoRepositorio {
    List<Grado> buscar(Criteria criterio);

    int registrar(Grado grado);

    int actualizar(Grado grado);

    void eliminar(int idGrado);
}
