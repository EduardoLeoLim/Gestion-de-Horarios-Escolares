package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.actualizar.ServicioActualizadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

public class GestionarEstatusMaestro {

    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioActualizadorMaestro actualizadorMaestro;

    public GestionarEstatusMaestro(ServicioBuscador<Maestro> buscadorMaestro,
                                   ServicioActualizadorMaestro actualizadorMaestro) {
        this.buscadorMaestro = buscadorMaestro;
        this.actualizadorMaestro = actualizadorMaestro;
    }

    public void habilitarMaestro(int idMaestro) throws RecursoNoEncontradoException {
        Maestro maestro = buscadorMaestro
                .igual("id", String.valueOf(idMaestro))
                .buscarPrimero();
        maestro.habilitar();
        actualizadorMaestro.actualizar(maestro);
    }

    public void deshabilitarMaestro(int idMaestro) throws RecursoNoEncontradoException {
        Maestro maestro = buscadorMaestro
                .igual("id", String.valueOf(idMaestro))
                .buscarPrimero();
        maestro.deshabilitar();
        actualizadorMaestro.actualizar(maestro);
    }
}
