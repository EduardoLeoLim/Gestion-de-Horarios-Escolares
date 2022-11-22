package com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.EvaluacionRepositorio;
import org.sql2o.Connection;

import java.util.List;

public class MySql2oEvaluacionRepositorio implements EvaluacionRepositorio {

    private final Connection connection;

    public MySql2oEvaluacionRepositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Evaluacion> buscar(Criteria criterio) {
        return null;
    }
}
