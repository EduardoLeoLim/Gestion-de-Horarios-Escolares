package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class MateriaClaseData {
    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Integer horasPracticas;
    private final Integer horasTeoricas;

    public MateriaClaseData(Integer id, String clave, String nombre, Integer horasPracticas, Integer horasTeoricas) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
    }

    public static MateriaClaseData fromAggregate(Materia materia) {
        return new MateriaClaseData(materia.id(), materia.clave(), materia.nombre(), materia.horasPracticas(), materia.horasTeoricas());
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

    public Integer horasPracticas() {
        return horasPracticas;
    }

    public Integer horasTeoricas() {
        return horasTeoricas;
    }
}
