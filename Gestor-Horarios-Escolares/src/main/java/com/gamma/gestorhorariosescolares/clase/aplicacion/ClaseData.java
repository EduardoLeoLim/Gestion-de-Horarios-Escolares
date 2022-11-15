package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;

public class ClaseData {

    private final Integer id;
    private final GrupoData grupo;
    private final MateriaData materia;
    private final MaestroData maestro;

    public ClaseData(Integer id, GrupoData grupo, MateriaData materia, MaestroData maestro) {
        this.id = id;
        this.grupo = grupo;
        this.materia = materia;
        this.maestro = maestro;
    }

    public static ClaseData fromAggregate(Clase clase, GrupoData grupo, MateriaData materia, MaestroData maestro) {
        return new ClaseData(clase.id().value(), grupo, materia, maestro);
    }

    public Integer id() {
        return id;
    }

    public GrupoData grupo() {
        return grupo;
    }

    public MateriaData materia() {
        return materia;
    }

    public MaestroData maestro() {
        return maestro;
    }
}
