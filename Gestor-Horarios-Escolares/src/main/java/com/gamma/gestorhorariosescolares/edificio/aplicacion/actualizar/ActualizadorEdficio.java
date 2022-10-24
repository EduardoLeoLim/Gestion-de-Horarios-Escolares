package com.gamma.gestorhorariosescolares.edificio.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.EdificioRepositorio;

public class ActualizadorEdficio implements ServicioActualizadorEdificio {

    private final EdificioRepositorio repositorio;

    public ActualizadorEdficio(EdificioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Edificio edificio) {
        //Validar formato antes de actualizar
        repositorio.actualizar(edificio);
    }

}