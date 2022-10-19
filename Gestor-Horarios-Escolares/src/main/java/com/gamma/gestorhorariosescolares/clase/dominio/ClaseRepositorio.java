package com.gamma.gestorhorariosescolares.clase.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface ClaseRepositorio {
    List<Clase> buscar(Criteria criterio);

    int registar(Clase clase);

    void actualizar(Clase clase);
}
