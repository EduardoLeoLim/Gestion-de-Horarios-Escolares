package com.gamma.gestorhorariosescolares.periodoescolar.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface PeriodoEscolarRepositorio {
    List<PeriodoEscolar> buscar(Criteria criteria);

    int registrar(PeriodoEscolar periodoEscolar);

    void actualizar(PeriodoEscolar periodoEscolar);
}
