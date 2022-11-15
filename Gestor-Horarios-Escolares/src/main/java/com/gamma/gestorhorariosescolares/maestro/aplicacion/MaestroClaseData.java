package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

public class MaestroClaseData {

    private final Integer id;
    private final String nombre;

    public MaestroClaseData(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static MaestroClaseData fromAggregate(Maestro maestro) {
        String nombre = maestro.nombre() + " " + maestro.apellidoPaterno() + " " + maestro.apellidoMaterno();
        return new MaestroClaseData(maestro.id().value(), nombre);
    }

    public Integer id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }
}
