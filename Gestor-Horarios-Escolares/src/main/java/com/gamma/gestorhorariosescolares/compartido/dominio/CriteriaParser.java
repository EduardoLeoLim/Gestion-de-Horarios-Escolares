package com.gamma.gestorhorariosescolares.compartido.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface CriteriaParser {

    void agregarCampos(String... columnas);

    void agregarCriteria(Criteria criteria);

    String generarConsulta();

}
