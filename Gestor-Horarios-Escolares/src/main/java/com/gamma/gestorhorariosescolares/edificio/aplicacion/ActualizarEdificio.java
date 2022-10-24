package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ActualizacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.actualizar.ServicioActualizadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

import java.util.List;

public class ActualizarEdificio {

    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioActualizadorEdificio actualizadorEdificio;

    public ActualizarEdificio(ServicioBuscador<Edificio> buscadorEdificio, ServicioActualizadorEdificio actualizadorEdificio) {
        this.buscadorEdificio = buscadorEdificio;
        this.actualizadorEdificio = actualizadorEdificio;
    }

    private void actualizar(EdificioData edificioData) throws RecursoNoEncontradoException, ActualizacionInvalidaException {
        if (edificioData == null)
            throw new NullPointerException();

        List<Edificio> listaBusquedaEdificio;

        //¿El edificio realmente se encuentra registrado?
        listaBusquedaEdificio = buscadorEdificio
                .igual("id", String.valueOf(edificioData.id()))
                .buscar();
        if (!(listaBusquedaEdificio.size() > 0))
            throw new RecursoNoEncontradoException("El edificio no se encuentra registrado en el sistema");

        //¿Otro edificio tiene la clave que se desea asignar a este edificio?
        listaBusquedaEdificio = buscadorEdificio
                .diferente("id", String.valueOf(edificioData.id()))
                .igual("clave", edificioData.clave())
                .buscar();
        if (listaBusquedaEdificio.size() > 0)
            throw new ActualizacionInvalidaException("Ya hay un edificio registrado con la clave " + edificioData.clave());

        Edificio edificio = new Edificio(edificioData.id(), edificioData.clave(), edificioData.nombre(), edificioData.estatus());

        actualizadorEdificio.actualizar(edificio);
    }
}
