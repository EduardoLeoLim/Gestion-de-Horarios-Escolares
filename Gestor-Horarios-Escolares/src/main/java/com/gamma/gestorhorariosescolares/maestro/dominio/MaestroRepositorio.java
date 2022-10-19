package com.gamma.gestorhorariosescolares.maestro.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface MaestroRepositorio {
    List<Maestro> buscar(Criteria criterio);

    int registrar(Maestro maestro);

    void actualizar(Maestro maestro);
}
