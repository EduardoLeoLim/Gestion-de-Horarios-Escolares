package com.gamma.gestorhorariosescolares.aplicacion.usuario;

import com.gamma.gestorhorariosescolares.dominio.usuario.UsuarioRepositorio;

public class ServicioRegistradorUsuario implements RegistradorUsuario{

    public final UsuarioRepositorio repositorio;

    public ServicioRegistradorUsuario(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public int registrar() {
        return 0;
    }
}
