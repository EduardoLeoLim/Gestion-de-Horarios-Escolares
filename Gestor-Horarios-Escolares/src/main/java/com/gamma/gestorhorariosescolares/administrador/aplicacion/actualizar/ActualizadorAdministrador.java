package com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;

public class ActualizadorAdministrador implements ServicioActualizadorAdministrador {
    private final AdministradorRepositorio repositorio;

    public ActualizadorAdministrador(AdministradorRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Administrador administrador) {
        repositorio.actualizar(administrador);
    }
}
