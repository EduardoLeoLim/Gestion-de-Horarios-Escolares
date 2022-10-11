package com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

import java.util.List;

public class BuscadorUsuario implements ServicioBuscadorUsuario {

    public final UsuarioRepositorio repositorio;

    public BuscadorUsuario(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public ServicioBuscadorUsuario filtarCorreo(String correo) {
        return null;
    }

    @Override
    public List<Usuario> buscar() {
        return null;
    }
}
