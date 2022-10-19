package com.gamma.gestorhorariosescolares.secretario.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface SecretarioRepositorio {
    List<Secretario> buscar(Criteria criterio);

    int registrar(Secretario secretario);

    void actualizar(Secretario secretario);
}
