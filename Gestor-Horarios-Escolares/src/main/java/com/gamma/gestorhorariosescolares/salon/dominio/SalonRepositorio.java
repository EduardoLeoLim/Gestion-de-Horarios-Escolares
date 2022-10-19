package com.gamma.gestorhorariosescolares.salon.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface SalonRepositorio {
    List<Salon> buscar(Criteria criterio);

    int registrar(Salon salon);

    void actualizar(Salon salon);
}
