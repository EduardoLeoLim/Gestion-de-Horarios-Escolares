package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;

public class GrupoData {

    private final Integer id;
    private final String clave;
    private final String nombre;
    private final GradoData grado;
    private final PeriodoEscolarData periodoEscolar;

    public GrupoData(Integer id, String clave, String nombre, GradoData grado, PeriodoEscolarData periodoEscolar) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.grado = grado;
        this.periodoEscolar = periodoEscolar;
    }

    public static GrupoData fromAggregate(Grupo grupo, GradoData grado, PeriodoEscolarData periodoEscolar) {
        return new GrupoData(grupo.id(), grupo.clave(), grupo.nombre(), grado, periodoEscolar);
    }

    public Integer id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public String nombre() {
        return nombre;
    }

    public GradoData grado() {
        return grado;
    }

    public PeriodoEscolarData periodoEscolar() {
        return periodoEscolar;
    }
}
