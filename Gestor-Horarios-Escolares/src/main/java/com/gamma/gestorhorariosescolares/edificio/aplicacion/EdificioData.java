package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

public class EdificioData {
    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Boolean estatus;

    public EdificioData(Integer id, String clave, String nombre, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    public static EdificioData fromAggregate(Edificio edificio) {
        return new EdificioData(edificio.id(), edificio.clave(), edificio.nombre(), edificio.estatus());
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

    public Boolean estatus() {
        return estatus;
    }
}
