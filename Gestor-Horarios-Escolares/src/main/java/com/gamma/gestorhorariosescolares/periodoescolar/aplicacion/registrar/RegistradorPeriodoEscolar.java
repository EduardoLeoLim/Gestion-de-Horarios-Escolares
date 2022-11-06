package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolarRepositorio;

import java.util.Date;

public class RegistradorPeriodoEscolar implements ServicioRegistradorPeriodoEscolar {

    private final PeriodoEscolarRepositorio repositorio;

    public RegistradorPeriodoEscolar(PeriodoEscolarRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, String nombre, Date fechaInicio, Date fechaFin) {
        //Validar formato de datos
        PeriodoEscolar periodoEscolar = new PeriodoEscolar(clave, nombre, fechaInicio, fechaFin);
        return repositorio.registrar(periodoEscolar);
    }
}
