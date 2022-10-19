package com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grado.dominio.GradoRepositorio;

import java.util.List;

public class MySql2oGradoRepositorio implements GradoRepositorio {
    @Override
    public List<Grado> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Grado grado) {
        return 0;
    }

    @Override
    public int actualizar(Grado grado) {
        return 0;
    }
}
