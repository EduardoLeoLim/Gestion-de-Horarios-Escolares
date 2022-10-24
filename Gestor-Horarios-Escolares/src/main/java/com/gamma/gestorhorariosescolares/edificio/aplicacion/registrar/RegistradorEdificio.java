package com.gamma.gestorhorariosescolares.edificio.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.EdificioRepositorio;

public class RegistradorEdificio implements ServicioRegistradorEdificio {

    private final EdificioRepositorio repositorio;

    public RegistradorEdificio(EdificioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, String nombre) {
        Edificio edificio = new Edificio(clave, nombre);
        //Aqui válidar formato de datos
        return repositorio.registrar(edificio);
    }

}