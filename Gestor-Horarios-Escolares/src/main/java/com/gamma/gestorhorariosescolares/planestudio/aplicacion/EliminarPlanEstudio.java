package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.EliminacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.eliminar.ServicioEliminadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;

public class EliminarPlanEstudio {

    private final ServicioBuscador<PlanEstudio> buscadorPlanEstudio;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final ServicioEliminadorPlanEstudio eliminadorPlanEstudio;

    public EliminarPlanEstudio(ServicioBuscador<PlanEstudio> buscadorPlanEstudio, ServicioBuscador<Grado> buscadorGrado,
                               ServicioEliminadorPlanEstudio eliminadorPlanEstudio) {
        this.buscadorPlanEstudio = buscadorPlanEstudio;
        this.buscadorGrado = buscadorGrado;
        this.eliminadorPlanEstudio = eliminadorPlanEstudio;
    }

    public void eliminar(int idPlanEstudio) throws RecursoNoEncontradoException, EliminacionInvalidaException {
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio
                .igual("id", String.valueOf(idPlanEstudio))
                .buscar();
        if (planesEstudio.isEmpty())
            throw new RecursoNoEncontradoException("El plan de estudio no se encuentra registrado en el sistema");

        List<Grado> grados = buscadorGrado
                .igual("idPlanEstudio", String.valueOf(idPlanEstudio))
                .buscar();
        if (!grados.isEmpty())
            throw new EliminacionInvalidaException("No se puede eliminar el Plan de estudio ya que tiene grados registrados.");

        eliminadorPlanEstudio.eliminar(idPlanEstudio);
    }
}
