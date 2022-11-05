package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolarRepositorio;

public class ActualizadorPeriodoEscolar implements ServicioActualizadorPeriodoEscolar {

    private final PeriodoEscolarRepositorio repositorio;

    public ActualizadorPeriodoEscolar(PeriodoEscolarRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(PeriodoEscolar periodoEscolar) {
        //Validar formato de datos aquí
        repositorio.actualizar(periodoEscolar);
    }

}