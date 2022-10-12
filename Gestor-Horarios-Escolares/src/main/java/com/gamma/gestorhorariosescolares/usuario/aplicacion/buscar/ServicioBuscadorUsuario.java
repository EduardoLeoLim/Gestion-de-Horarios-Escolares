package com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public interface ServicioBuscadorUsuario {
    ServicioBuscadorUsuario filtarCorreo(String correo);

    List<Usuario> buscar();
}
