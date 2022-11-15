package com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.EvaluacionRepositorio;

import java.util.List;

public class MySql2oEvaluacionRepositorio implements EvaluacionRepositorio {
    @Override
    public List<Evaluacion> buscar(Criteria criterio) {
        return null;
    }
}
