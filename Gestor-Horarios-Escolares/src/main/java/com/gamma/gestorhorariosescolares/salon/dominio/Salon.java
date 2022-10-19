package com.gamma.gestorhorariosescolares.salon.dominio;

public class Salon {
    private final int id;
    private final String clave;
    private final int capacidad;
    private boolean estatus;
    private final int idEdificio;

    public Salon(int id, String clave, int capacidad, boolean estatus, int idEdificio) {
        this.id = id;
        this.clave = clave;
        this.capacidad = capacidad;
        this.estatus = estatus;
        this.idEdificio = idEdificio;
    }

    public Salon(String clave, int capacidad, int idEdificio) {
        id = 0;
        estatus = true;
        this.clave = clave;
        this.capacidad = capacidad;
        this.idEdificio = idEdificio;
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

    public int idEdificio() {
        return idEdificio;
    }
}
