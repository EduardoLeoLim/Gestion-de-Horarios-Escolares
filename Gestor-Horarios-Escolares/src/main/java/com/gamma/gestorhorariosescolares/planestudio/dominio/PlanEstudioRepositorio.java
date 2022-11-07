package com.gamma.gestorhorariosescolares.planestudio.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface PlanEstudioRepositorio {
    List<PlanEstudio> buscar(Criteria criterio);

    int registrar(PlanEstudio planEstudio);

    void actualizar(PlanEstudio planEstudio);

    void eliminar(int idPlanEstudio);
}
