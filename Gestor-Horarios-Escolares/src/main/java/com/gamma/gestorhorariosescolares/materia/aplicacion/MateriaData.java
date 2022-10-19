package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class MateriaData {
    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Integer horasPracticas;
    private final Integer horasTeoricas;
    private final Boolean estatus;

    public MateriaData(Integer id, String clave, String nombre, Integer horasPracticas, Integer horasTeoricas, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
        this.estatus = estatus;
    }

    public static MateriaData fromAggregate(Materia materia) {
        return new MateriaData(materia.id(), materia.clave(), materia.nombre(), materia.horasPracticas(),
                materia.horasTeoricas(), materia.estatus());
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

    public Boolean estatus() {
        return estatus;
    }
}
