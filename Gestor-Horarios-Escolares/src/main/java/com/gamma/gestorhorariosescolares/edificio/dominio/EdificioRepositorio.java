package com.gamma.gestorhorariosescolares.edificio.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface EdificioRepositorio {
    List<Edificio> buscar(Criteria criterio);

    int registrar(Edificio edificio);

    void actualizar(Edificio edificio);
}
