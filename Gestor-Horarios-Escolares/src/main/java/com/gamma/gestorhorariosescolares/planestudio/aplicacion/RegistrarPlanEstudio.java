package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.registrar.RegistradorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;

public class RegistrarPlanEstudio {

    private final ServicioBuscador<PlanEstudio> buscadorPlanEstudio;
    private final RegistradorPlanEstudio registradorPlanEstudio;

    public RegistrarPlanEstudio(ServicioBuscador<PlanEstudio> buscadorPlanEstudio,
                                RegistradorPlanEstudio registradorPlanEstudio) {
        this.buscadorPlanEstudio = buscadorPlanEstudio;
        this.registradorPlanEstudio = registradorPlanEstudio;
    }

    public void registrar(String clave, String nombre) throws ClaveDuplicadaException {
        List<PlanEstudio> planesEstudios = buscadorPlanEstudio
                .igual("clave", clave)
                .buscar();
        if (!planesEstudios.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un plan de estudio registrado con la clave " + clave);
        registradorPlanEstudio.registrar(clave, nombre);
    }
}
