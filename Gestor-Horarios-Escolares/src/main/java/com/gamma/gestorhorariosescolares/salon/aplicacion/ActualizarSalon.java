package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar.ServicioActualizadorSalon;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.util.List;

public class ActualizarSalon {

    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioActualizadorSalon actualizadorSalon;

    public ActualizarSalon(ServicioBuscador<Edificio> buscadorEdificio,
                           ServicioBuscador<Salon> buscadorSalon,
                           ServicioActualizadorSalon actualizadorSalon) {
        this.buscadorEdificio = buscadorEdificio;
        this.buscadorSalon = buscadorSalon;
        this.actualizadorSalon = actualizadorSalon;
    }

    public void actualizar(SalonData salonData) throws RecursoNoEncontradoException, ClaveDuplicadaException {
        if (salonData == null)
            throw new NullPointerException();

        List<Salon> listaBusquedaSalones;

        //¿Existe el salón que se desea actualizar?
        listaBusquedaSalones = buscadorSalon
                .igual("id", String.valueOf(salonData.id()))
                .buscar();
        if (listaBusquedaSalones.isEmpty())
            throw new RecursoNoEncontradoException("El salón que deseas actualizar no se encuentra registrado en el sistema");

        List<Edificio> listaBusquedaEdificios;

        //¿Existe el edificio?
        listaBusquedaEdificios = buscadorEdificio
                .igual("id", String.valueOf(salonData.edificio().id()))
                .buscar();
        if (listaBusquedaEdificios.isEmpty())
            throw new RecursoNoEncontradoException("El edificio no se encuentra registrado en el sistema");

        //¿Ya hay un salon registrado con la clave?
        List<Salon> listaBusquedaSalon = buscadorSalon
                .diferente("id", String.valueOf(salonData.id()))//se descarta al propio salón
                .igual("clave", salonData.clave())
                .buscar();
        if (!listaBusquedaSalon.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un salón registrado con la clave " + salonData.clave());

        Salon salon = new Salon(salonData.id(), salonData.clave(), salonData.capacidad(), salonData.estatus(),
                salonData.edificio().id());

        actualizadorSalon.actualizar(salon);
    }

}
