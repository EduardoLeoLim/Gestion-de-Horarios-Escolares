package com.gamma.gestorhorariosescolares.dominio.usuario;

import com.gamma.gestorhorariosescolares.dominio.compartido.Criterio;

import java.util.List;

public interface UsuarioRepositorio {
    List<Usuario> buscar(Criterio criterio);

    int registrar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminar(int idUsuario);
}
