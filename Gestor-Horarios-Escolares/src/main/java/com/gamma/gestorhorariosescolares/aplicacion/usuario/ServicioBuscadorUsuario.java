package com.gamma.gestorhorariosescolares.aplicacion.usuario;

import com.gamma.gestorhorariosescolares.dominio.usuario.Usuario;
import com.gamma.gestorhorariosescolares.dominio.usuario.UsuarioRepositorio;

import java.util.List;

public class ServicioBuscadorUsuario implements BuscadorUsuario{

    public final UsuarioRepositorio repositorio;

    public ServicioBuscadorUsuario(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public BuscadorUsuario filtarCorreo(String correo) {
        return null;
    }

    @Override
    public List<Usuario> buscar() {
        return null;
    }
}
