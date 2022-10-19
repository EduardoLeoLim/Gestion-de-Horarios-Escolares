package com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudioRepositorio;

import java.util.List;

public class MySql2oPlanEstudioRepositorio implements PlanEstudioRepositorio {
    @Override
    public List<PlanEstudio> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(PlanEstudio planEstudio) {
        return 0;
    }

    @Override
    public void actualizar(PlanEstudio planEstudio) {

    }
}
