package com.gamma.gestorhorariosescolares.grupo.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface GrupoRepositorio {
    List<Grupo> buscar(Criteria criterio);

    int registrar(Grupo grupo);

    void actualizar(Grupo grupo);
}
