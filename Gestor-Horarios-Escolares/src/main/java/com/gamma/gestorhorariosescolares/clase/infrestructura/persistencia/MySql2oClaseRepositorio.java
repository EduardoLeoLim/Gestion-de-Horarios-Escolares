package com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.clase.dominio.ClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public class MySql2oClaseRepositorio implements ClaseRepositorio {
    @Override
    public List<Clase> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registar(Clase clase) {
        return 0;
    }

    @Override
    public void actualizar(Clase clase) {

    }
}
