package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

public class ClaseMaestroData {

    private final Integer id;
    private final String nombre;

    public ClaseMaestroData(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;

    }

    public static ClaseMaestroData fromAggregate(Maestro maestro){
        return new ClaseMaestroData(maestro.id().value(), maestro.nombre());

    }

    public int id() {
        return id;
    }
    public String nombre() {
        return nombre;
    }


}
