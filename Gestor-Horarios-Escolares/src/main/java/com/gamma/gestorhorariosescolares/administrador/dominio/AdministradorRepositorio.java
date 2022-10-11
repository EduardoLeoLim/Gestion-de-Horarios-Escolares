package com.gamma.gestorhorariosescolares.administrador.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;

import java.util.List;

public interface AdministradorRepositorio {
    List<Administrador> buscar(Criterio criterio);

    int registrar(Administrador administrador);

    void actualizar(Administrador administrador);

    void eliminar(int idAdministrador);
}
