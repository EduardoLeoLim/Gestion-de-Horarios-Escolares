package com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

public class ActualizadorUsuario implements ServicioActualizadorUsuario {

    private final UsuarioRepositorio repositorio;

    public ActualizadorUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Usuario usuario) {
        //validar formato datos antes de actualizar
        repositorio.actualizar(usuario);
    }
}
