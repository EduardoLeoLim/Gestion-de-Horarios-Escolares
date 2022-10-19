package com.gamma.gestorhorariosescolares.usuario.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface UsuarioRepositorio {
    List<Usuario> buscar(Criteria criterio);

    int registrar(Usuario usuario);

    void actualizar(Usuario usuario);
}
