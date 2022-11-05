package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.util.List;
import java.util.stream.Collectors;

public class PeriodosEscolaresData {

    private final List<PeriodoEscolarData> periodosEscolares;

    public PeriodosEscolaresData(List<PeriodoEscolar> periodosEscolares) {
        this.periodosEscolares = periodosEscolares.stream().map(PeriodoEscolarData::fromAggregate).collect(Collectors.toList());
    }

    public List<PeriodoEscolarData> periodosEscolares() {
        return periodosEscolares;
    }
}
