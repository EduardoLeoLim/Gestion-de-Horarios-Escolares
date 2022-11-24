package com.gamma.gestorhorariosescolares.evaluacion.dominio;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface EvaluacionRepositorio {

    List<Evaluacion> buscar(Criteria criterio);

    int registar(Evaluacion evaluacion);

}
