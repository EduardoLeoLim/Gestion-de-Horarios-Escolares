package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroClaseData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaClaseData;

public class ClaseGrupoData {

    private final Integer id;
    private final MateriaClaseData materia;
    private final MaestroClaseData maestro;

    public ClaseGrupoData(Integer id, MateriaClaseData materia, MaestroClaseData maestro) {
        this.id = id;
        this.materia = materia;
        this.maestro = maestro;
    }

    public static ClaseGrupoData fromAggregate(Clase clase, MateriaClaseData materia, MaestroClaseData maestro) {
        return new ClaseGrupoData(clase.id().value(), materia, maestro);
    }

    public Integer id() {
        return id;
    }

    public MateriaClaseData materia() {
        return materia;
    }

    public MaestroClaseData maestro() {
        return maestro;
    }
}
