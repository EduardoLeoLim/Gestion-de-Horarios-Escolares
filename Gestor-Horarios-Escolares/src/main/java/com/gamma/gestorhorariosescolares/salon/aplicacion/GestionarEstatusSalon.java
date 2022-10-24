package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar.ServicioActualizadorSalon;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.util.List;

public class GestionarEstatusSalon {

    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioActualizadorSalon actualizadorSalon;

    public GestionarEstatusSalon(ServicioBuscador<Salon> buscadorSalon, ServicioActualizadorSalon actualizadorSalon) {
        this.buscadorSalon = buscadorSalon;
        this.actualizadorSalon = actualizadorSalon;
    }

    public void habilitarSalon(int idSalon) throws RecursoNoEncontradoException {
        List<Salon> salones;
        salones = buscadorSalon.igual("id", String.valueOf(idSalon)).buscar();
        if (salones.isEmpty())
            throw new RecursoNoEncontradoException("El salón no se encuentra registrado");

        Salon salon = salones.get(0);
        salon.habilitar();
        actualizadorSalon.actualizar(salon);
    }

    public void deshabilitarSalon(int idSalon) throws RecursoNoEncontradoException {
        List<Salon> salones;
        salones = buscadorSalon.igual("id", String.valueOf(idSalon)).buscar();
        if (salones.isEmpty())
            throw new RecursoNoEncontradoException("El salón no se encuentra registrado");

        Salon salon = salones.get(0);
        salon.deshabilitar();
        actualizadorSalon.actualizar(salon);
    }

}
