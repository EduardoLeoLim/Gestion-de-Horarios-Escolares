package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.registrar.ServicioRegistradorEdificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

import java.util.List;

public class RegistrarEdificio {

    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioRegistradorEdificio registradorEdificio;

    public RegistrarEdificio(ServicioBuscador<Edificio> buscadorEdificio, ServicioRegistradorEdificio registradorEdificio) {
        this.buscadorEdificio = buscadorEdificio;
        this.registradorEdificio = registradorEdificio;
    }

    public void registrar(String clave, String nombre) throws ClaveDuplicadaException {
        List<Edificio> listaBusquedaEdificio;
        listaBusquedaEdificio = buscadorEdificio.igual("clave", clave).buscar();

        if (!listaBusquedaEdificio.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un edificio registrado con la clave " + clave);

        registradorEdificio.registrar(clave, nombre);
    }

}