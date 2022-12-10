package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class ClaseMateriaData {
    private final Integer id;
    private final String clave;
    private final String nombre;

    public ClaseMateriaData(Integer id, String clave, String nombre){
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;

    }

    public static ClaseMateriaData fromAggregate(Materia materia){
        return new ClaseMateriaData(materia.id(), materia.clave(), materia.nombre());

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
}
