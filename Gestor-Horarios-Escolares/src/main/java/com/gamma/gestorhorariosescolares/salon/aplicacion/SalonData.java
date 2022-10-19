package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

public class SalonData {
    private final Integer id;
    private final String clave;
    private final Integer capacidad;
    private final Boolean estatus;
    private final EdificioData edificio;

    public SalonData(Integer id, String clave, Integer capacidad, Boolean estatus, EdificioData edificio) {
        this.id = id;
        this.clave = clave;
        this.capacidad = capacidad;
        this.estatus = estatus;
        this.edificio = edificio;
    }

    public static SalonData fromAggregate(Salon salon, Edificio edificio) {
        EdificioData edificioData = EdificioData.fromAggregate(edificio);
        return new SalonData(salon.id(), salon.clave(), salon.capacidad(), salon.estatus(), edificioData);
    }

    public Integer id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public Integer capacidad() {
        return capacidad;
    }

    public Boolean estatus() {
        return estatus;
    }

    public EdificioData edificio() {
        return edificio;
    }
}
