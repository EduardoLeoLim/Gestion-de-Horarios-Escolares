package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.actualizar.ServicioActualizadorClase;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

public class AsignarMaestro {

    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioActualizadorClase actualizadorClase;

    public AsignarMaestro(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Maestro> buscadorMaestro,
                          ServicioActualizadorClase actualizadorClase) {
        this.buscadorClase = buscadorClase;
        this.buscadorMaestro = buscadorMaestro;
        this.actualizadorClase = actualizadorClase;
    }

    public void asignarMaestro(int idClase, int idMaestro) throws RecursoNoEncontradoException {
        Clase clase = buscadorClase
                .igual("id", String.valueOf(idClase))
                .buscarPrimero();

        Maestro maestro = buscadorMaestro
                .igual("id", String.valueOf(idMaestro))
                .igual("estatus", "1")
                .buscarPrimero();

        clase.asignarIdMaestro(maestro.id().value());

        actualizadorClase.actualizar(clase);
    }

}