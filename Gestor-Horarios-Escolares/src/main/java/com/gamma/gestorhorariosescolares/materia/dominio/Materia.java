package com.gamma.gestorhorariosescolares.materia.dominio;

public class Materia {
    private final int id;
    private final String clave;
    private final String nombre;
    private final int horasPracticas;
    private final int horasTeoricas;
    private boolean estatus;

    public Materia(int id, String clave, String nombre, int horasPracticas, int horasTeoricas, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
        this.estatus = estatus;
    }

    public Materia(String clave, String nombre, int horasPracticas, int horasTeoricas) {
        id = 0;
        estatus = true;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
    }

    public int id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public String nombre() {
        return nombre;
    }

    public int horasPracticas() {
        return horasPracticas;
    }

    public int horasTeoricas() {
        return horasTeoricas;
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
