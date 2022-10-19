package com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolarRepositorio;

import java.util.List;

public class MySql2oPeriodoEscolarRepositorio implements PeriodoEscolarRepositorio {
    @Override
    public List<PeriodoEscolar> buscar(Criteria criteria) {
        return null;
    }

    @Override
    public int registrar(PeriodoEscolar periodoEscolar) {
        return 0;
    }

    @Override
    public void actualizar(PeriodoEscolar periodoEscolar) {

    }
}
