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
        Edificio edificio = buscadorEdificio
                .igual("id", String.valueOf(idEdificio))
                .buscarPrimero();
        edificio.habilitar();
        actualizadorEdificio.actualizar(edificio);
    }

    public void deshabilitarEdificio(int idEdificio) throws RecursoNoEncontradoException {
        Edificio edificio = buscadorEdificio
                .igual("id", String.valueOf(idEdificio))
                .buscarPrimero();
        edificio.deshabilitar();
        actualizadorEdificio.actualizar(edificio);
    }

}
