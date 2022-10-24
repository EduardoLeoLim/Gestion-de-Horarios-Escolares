package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.actualizar.ServicioActualizadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

import java.util.List;

public class GestionarEstatusEdificio {

    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioActualizadorEdificio actualizadorEdificio;

    public GestionarEstatusEdificio(ServicioBuscador<Edificio> buscadorEdificio, ServicioActualizadorEdificio actualizadorEdificio) {
        this.buscadorEdificio = buscadorEdificio;
        this.actualizadorEdificio = actualizadorEdificio;
    }

    public void habilitarEdificio(int idEdificio) throws RecursoNoEncontradoException {
        List<Edificio> edificios;
        edificios = buscadorEdificio.igual("id", String.valueOf(idEdificio)).buscar();
        if (edificios.isEmpty())
            throw new RecursoNoEncontradoException("El edificio no se encuentra registrado en el siste,a");

        Edificio edificio = edificios.get(0);
        edificio.habilitar();
        actualizadorEdificio.actualizar(edificio);
    }

    public void deshabilitarEdificio(int idEdificio) throws RecursoNoEncontradoException {
        List<Edificio> edificios;
        edificios = buscadorEdificio.igual("id", String.valueOf(idEdificio)).buscar();
        if (edificios.isEmpty())
            throw new RecursoNoEncontradoException("El edificio no se encuentra registrado en el siste,a");

        Edificio edificio = edificios.get(0);
        edificio.deshabilitar();
        actualizadorEdificio.actualizar(edificio);
    }

}
