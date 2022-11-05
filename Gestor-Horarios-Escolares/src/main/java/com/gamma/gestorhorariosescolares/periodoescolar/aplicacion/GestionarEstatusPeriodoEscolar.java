package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.actualizar.ActualizadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

public class GestionarEstatusPeriodoEscolar {

    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;
    private final ActualizadorPeriodoEscolar actualizadorPeriodoEscolar;

    public GestionarEstatusPeriodoEscolar(ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar,
                                          ActualizadorPeriodoEscolar actualizadorPeriodoEscolar) {
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
        this.actualizadorPeriodoEscolar = actualizadorPeriodoEscolar;
    }

    public void habilitarPeriodoEscolar(int idPeriodoEscolar) throws RecursoNoEncontradoException {
        PeriodoEscolar periodoEscolar = buscadorPeriodoEscolar
                .igual("id", String.valueOf(idPeriodoEscolar))
                .buscarPrimero();

        periodoEscolar.habilitar();

        actualizadorPeriodoEscolar.actualizar(periodoEscolar);
    }

    public void deshabilitarPeriodoEscolar(int idPeriodoEscolar) throws RecursoNoEncontradoException {
        PeriodoEscolar periodoEscolar = buscadorPeriodoEscolar
                .igual("id", String.valueOf(idPeriodoEscolar))
                .buscarPrimero();

        periodoEscolar.deshabilitar();

        actualizadorPeriodoEscolar.actualizar(periodoEscolar);
    }
}
