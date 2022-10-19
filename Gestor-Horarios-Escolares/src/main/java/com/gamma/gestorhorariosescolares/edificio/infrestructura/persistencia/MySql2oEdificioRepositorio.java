package com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.EdificioRepositorio;

import java.util.List;

public class MySql2oEdificioRepositorio implements EdificioRepositorio {
    @Override
    public List<Edificio> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Edificio edificio) {
        return 0;
    }

    @Override
    public void actualizar(Edificio edificio) {

    }
}
