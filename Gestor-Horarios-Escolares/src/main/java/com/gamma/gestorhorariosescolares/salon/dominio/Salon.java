package com.gamma.gestorhorariosescolares.salon.dominio;

public class Salon {
    private final int id;
    private final String clave;
    private final int capacidad;
    private boolean estatus;

    public Salon(int id, String clave, int capacidad, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.capacidad = capacidad;
        this.estatus = estatus;
    }

    public Salon(String clave, int capacidad) {
        id = 0;
        estatus = true;
        this.clave = clave;
        this.capacidad = capacidad;
    }

    public int id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public int capacidad() {
        return capacidad;
    }

    public void habilitar() {
        estatus = true;
    }

    public void deshabilitar() {
        estatus = false;
    }

    public boolean estatus() {
        return estatus;
    }
}
