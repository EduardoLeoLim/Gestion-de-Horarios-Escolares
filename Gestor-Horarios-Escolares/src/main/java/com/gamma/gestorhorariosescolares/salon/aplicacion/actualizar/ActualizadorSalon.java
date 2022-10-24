package com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.salon.dominio.Salon;
import com.gamma.gestorhorariosescolares.salon.dominio.SalonRepositorio;

public class ActualizadorSalon implements ServicioActualizadorSalon {

    private final SalonRepositorio repositorio;

    public ActualizadorSalon(SalonRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Salon salon) {
        repositorio.actualizar(salon);
    }
}