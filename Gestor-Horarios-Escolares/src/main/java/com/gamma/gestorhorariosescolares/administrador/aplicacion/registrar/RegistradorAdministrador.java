package com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;

public class RegistradorAdministrador implements ServicioRegistradorAdministrador{
    private final AdministradorRepositorio repositorio;

    public RegistradorAdministrador(AdministradorRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        var administrador = new Administrador(noPersonal,nombre,apellidoPaterno,apellidoMaterno,idUsuario);
        repositorio.registrar(administrador);
    }
}
