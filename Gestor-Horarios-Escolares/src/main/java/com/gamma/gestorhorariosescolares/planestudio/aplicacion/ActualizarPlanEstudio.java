package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.actualizar.ActualizadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;

public class ActualizarPlanEstudio {

    private final ServicioBuscador<PlanEstudio> buscadorPlanEstudio;
    private final ActualizadorPlanEstudio actualizadorPlanEstudio;

    public ActualizarPlanEstudio(ServicioBuscador<PlanEstudio> buscadorPlanEstudio,
                                 ActualizadorPlanEstudio actualizadorPlanEstudio) {
        this.buscadorPlanEstudio = buscadorPlanEstudio;
        this.actualizadorPlanEstudio = actualizadorPlanEstudio;
    }

    public void actualizar(PlanEstudioData planEstudioData) throws RecursoNoEncontradoException, ClaveDuplicadaException {
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio
                .igual("id", String.valueOf(planEstudioData.id()))
                .buscar();
        if (planesEstudio.isEmpty())
            throw new RecursoNoEncontradoException("El plan de estudio que se desea actualizar, no se encuentra registrado en el sistema");

        planesEstudio = buscadorPlanEstudio
                .diferente("id", String.valueOf(planEstudioData.id()))
                .igual("clave", planEstudioData.clave())
                .buscar();
        if (!planesEstudio.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un plan de estudio registrado con la clave " + planEstudioData.clave());

        PlanEstudio planEstudio = new PlanEstudio(planEstudioData.id(), planEstudioData.clave(), planEstudioData.nombre(), planEstudioData.estatus());
        actualizadorPlanEstudio.actualizar(planEstudio);
    }

}