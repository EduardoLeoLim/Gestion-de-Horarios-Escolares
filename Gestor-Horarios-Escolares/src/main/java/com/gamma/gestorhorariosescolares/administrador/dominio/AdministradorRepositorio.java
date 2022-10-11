package com.gamma.gestorhorariosescolares.administrador.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface AdministradorRepositorio {
    List<Administrador> buscar(Criteria criterio);

    int registrar(Administrador administrador);

    void actualizar(Administrador administrador);

    void eliminar(int idAdministrador);
}
