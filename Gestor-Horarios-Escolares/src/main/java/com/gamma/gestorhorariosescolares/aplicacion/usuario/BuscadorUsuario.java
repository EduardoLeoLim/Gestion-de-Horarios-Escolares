package com.gamma.gestorhorariosescolares.aplicacion.usuario;

import com.gamma.gestorhorariosescolares.dominio.usuario.Usuario;

import java.util.List;

public interface BuscadorUsuario {
    BuscadorUsuario filtarCorreo(String correo);
    List<Usuario> buscar();
}
