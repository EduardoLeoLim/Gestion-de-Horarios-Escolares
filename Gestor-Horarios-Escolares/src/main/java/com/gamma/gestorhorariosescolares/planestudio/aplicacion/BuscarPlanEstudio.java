package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;

public class BuscarPlanEstudio {

    private final ServicioBuscador<PlanEstudio> buscadorPlanEstudio;

    public BuscarPlanEstudio(ServicioBuscador<PlanEstudio> buscadorPlanEstudio) {
        this.buscadorPlanEstudio = buscadorPlanEstudio;
    }

    public PlanesEstudioData buscarTodos() {
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio
                .ordenarAscendente("clave")
                .buscar();

        return new PlanesEstudioData(planesEstudio);
    }

    public PlanesEstudioData buscarHabilitados() {
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio
                .igual("estatus", "1")
                .ordenarAscendente("clave")
                .buscar();

        return new PlanesEstudioData(planesEstudio);
    }

    public PlanesEstudioData buscarPorCriterio(String criterio){
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio
                .contiene("clave", criterio).esOpcional()
                .contiene("nombre",criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        return new PlanesEstudioData(planesEstudio);
    }

}
