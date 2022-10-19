package com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;
import com.gamma.gestorhorariosescolares.salon.dominio.SalonRepositorio;

import java.util.List;

public class MySql2oSalonRepositorio implements SalonRepositorio {
    @Override
    public List<Salon> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Salon salon) {
        return 0;
    }

    @Override
    public void actualizar(Salon salon) {

    }
}
