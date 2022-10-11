package com.gamma.gestorhorariosescolares.usuario.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;

import java.util.List;

public interface UsuarioRepositorio {
    List<Usuario> buscar(Criterio criterio);

    int registrar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminar(int idUsuario);
}
