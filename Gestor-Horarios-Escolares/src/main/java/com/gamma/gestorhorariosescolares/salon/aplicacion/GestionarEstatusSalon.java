package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar.ServicioActualizadorSalon;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

public class GestionarEstatusSalon {

    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioActualizadorSalon actualizadorSalon;

    public GestionarEstatusSalon(ServicioBuscador<Salon> buscadorSalon, ServicioActualizadorSalon actualizadorSalon) {
        this.buscadorSalon = buscadorSalon;
        this.actualizadorSalon = actualizadorSalon;
    }

    public void habilitarSalon(int idSalon) throws RecursoNoEncontradoException {
        Salon salon = buscadorSalon.igual("id", String.valueOf(idSalon)).buscarPrimero();
        salon.habilitar();
        actualizadorSalon.actualizar(salon);
    }

    public void deshabilitarSalon(int idSalon) throws RecursoNoEncontradoException {
        Salon salon = buscadorSalon.igual("id", String.valueOf(idSalon)).buscarPrimero();
        salon.deshabilitar();
        actualizadorSalon.actualizar(salon);
    }

}
