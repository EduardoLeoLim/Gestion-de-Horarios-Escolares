package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar.ServicioActualizadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;

import java.util.List;

public class GestionarEstatusAdministrador {

    private final ServicioBuscador<Administrador> buscadorAdministrador;
    private final ServicioActualizadorAdministrador actualizadorAdministrador;

    public GestionarEstatusAdministrador(ServicioBuscador<Administrador> buscadorAdministrador,
                                         ServicioActualizadorAdministrador actualizadorAdministrador) {
        this.buscadorAdministrador = buscadorAdministrador;
        this.actualizadorAdministrador = actualizadorAdministrador;
    }

    public void habilitarAdministrador(int idAdministrador) throws RecursoNoEncontradoException {
        List<Administrador> administradores = buscadorAdministrador.igual("id", String.valueOf(idAdministrador)).buscar();
        if (administradores.size() == 0)
            throw new RecursoNoEncontradoException("El administrador no se encuentra registrado en el sistema.");

        Administrador administrador = administradores.get(0);
        administrador.habilitar();
        actualizadorAdministrador.actualizar(administrador);
    }

    public void deshabilitarAdministrador(int idAdministrador) throws RecursoNoEncontradoException {
        List<Administrador> administradores = buscadorAdministrador.igual("id", String.valueOf(idAdministrador)).buscar();
        if (administradores.size() == 0)
            throw new RecursoNoEncontradoException("El administrador no se encuentra registrado en el sistema.");

        Administrador administrador = administradores.get(0);
        administrador.deshabilitar();
        actualizadorAdministrador.actualizar(administrador);
    }
}
