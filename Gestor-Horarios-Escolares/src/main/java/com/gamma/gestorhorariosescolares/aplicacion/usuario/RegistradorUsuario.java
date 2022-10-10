package com.gamma.gestorhorariosescolares.aplicacion.usuario;

import com.gamma.gestorhorariosescolares.dominio.usuario.UsuarioRepositorio;

public class RegistradorUsuario implements ServicioRegistradorUsuario {

    public final UsuarioRepositorio repositorio;

    public RegistradorUsuario(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public int registrar() {
        return 0;
    }
}
