package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar.ServicioActualizadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.ServicioBuscadorAdministrador;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;

public class GestionarEstatusAdministrador {

    private final ServicioBuscadorAdministrador buscadorAdministrador;
    private final ServicioActualizadorAdministrador actualizadorAdministrador;

    public GestionarEstatusAdministrador(ServicioBuscadorAdministrador buscadorAdministrador,
                                         ServicioActualizadorAdministrador actualizadorAdministrador){
        this.buscadorAdministrador = buscadorAdministrador;
        this.actualizadorAdministrador = actualizadorAdministrador;
    }

    public void habilitarAdministrador(int idAdministrador) throws RecursoNoEncontradoException {
        var administradores = buscadorAdministrador.filtrarId(idAdministrador).buscar();
        if(administradores.size() == 0)
            throw new RecursoNoEncontradoException("El administrador no se encuentra registrado en el sistema.");

        var administrador = administradores.get(0);
        administrador.habilitar();
        actualizadorAdministrador.actualizar(administrador);
    }

    public void deshabilitarAdministrador(int idAdministrador) throws RecursoNoEncontradoException {
        var administradores = buscadorAdministrador.filtrarId(idAdministrador).buscar();
        if(administradores.size() == 0)
            throw new RecursoNoEncontradoException("El administrador no se encuentra registrado en el sistema.");

        var administrador = administradores.get(0);
        administrador.deshabilitar();
        actualizadorAdministrador.actualizar(administrador);
    }
}
