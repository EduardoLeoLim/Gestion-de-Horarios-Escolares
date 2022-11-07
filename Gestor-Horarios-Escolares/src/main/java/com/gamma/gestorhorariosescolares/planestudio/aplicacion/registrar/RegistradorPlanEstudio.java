package com.gamma.gestorhorariosescolares.planestudio.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudioRepositorio;

public class RegistradorPlanEstudio implements ServicioRegistradorPlanEstudio {

    private final PlanEstudioRepositorio repositorio;

    public RegistradorPlanEstudio(PlanEstudioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, String nombre) {
        //Validar datos aquí
        PlanEstudio planEstudio = new PlanEstudio(clave, nombre);
        return repositorio.registrar(planEstudio);
    }

}