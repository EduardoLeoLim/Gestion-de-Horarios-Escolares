package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.util.List;

public class BuscarPeriodosEscolares {

    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;

    public BuscarPeriodosEscolares(ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar) {
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
    }

    public PeriodosEscolaresData buscarTodos() {
        List<PeriodoEscolar> periodosEscolares = buscadorPeriodoEscolar
                .ordenarAscendente("clave")
                .buscar();

        return new PeriodosEscolaresData(periodosEscolares);
    }

    public PeriodosEscolaresData buscarHabilitados() {
        List<PeriodoEscolar> periodosEscolares = buscadorPeriodoEscolar
                .igual("estatus","1")
                .ordenarAscendente("clave")
                .buscar();

        return new PeriodosEscolaresData(periodosEscolares);
    }

    public PeriodosEscolaresData buscarPorCriterio(String criterio) {
        List<PeriodoEscolar> periodosEscolares = buscadorPeriodoEscolar
                .contiene("clave", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("fechaInicio", criterio).esOpcional()
                .contiene("fechaFin", criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        return new PeriodosEscolaresData(periodosEscolares);
    }
}
