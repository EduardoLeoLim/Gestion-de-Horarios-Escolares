package com.gamma.gestorhorariosescolares.salon.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.salon.dominio.Salon;
import com.gamma.gestorhorariosescolares.salon.dominio.SalonRepositorio;

public class RegistradorSalon implements ServicioRegistradorSalon {

    private final SalonRepositorio repositorio;

    public RegistradorSalon(SalonRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, int capacidad, int idEdificio) {
        Salon salon = new Salon(clave, capacidad, idEdificio);
        //Validar datos antes de registrar
        return repositorio.registrar(salon);
    }
}
