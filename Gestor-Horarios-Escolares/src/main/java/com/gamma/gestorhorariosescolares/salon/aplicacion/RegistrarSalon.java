package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.registrar.ServicioRegistradorSalon;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.util.List;

public class RegistrarSalon {

    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioRegistradorSalon registradorSalon;

    public RegistrarSalon(ServicioBuscador<Edificio> buscadorEdificio, ServicioBuscador<Salon> buscadorSalon, ServicioRegistradorSalon registradorSalon) {
        this.buscadorEdificio = buscadorEdificio;
        this.buscadorSalon = buscadorSalon;
        this.registradorSalon = registradorSalon;
    }

    public void registrar(String clave, int capacidad, int idEdificio) throws RecursoNoEncontradoException, ClaveDuplicadaException {
        //¿Existe el edificio?
        List<Edificio> listaBusquedaEdificios = buscadorEdificio
                .igual("id", String.valueOf(idEdificio))
                .buscar();
        if (listaBusquedaEdificios.isEmpty())
            throw new RecursoNoEncontradoException("El edificio no se encuentra registrado en el sistema");

        //¿Ya hay un salon registrado con la clave?
        List<Salon> listaBusquedaSalon = buscadorSalon.igual("clave", clave).buscar();
        if (!listaBusquedaSalon.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un salón registrado con la clave " + clave);

        registradorSalon.registrar(clave, capacidad, idEdificio);
    }
}
