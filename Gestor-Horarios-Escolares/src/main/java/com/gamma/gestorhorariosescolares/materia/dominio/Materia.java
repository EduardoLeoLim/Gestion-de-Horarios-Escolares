package com.gamma.gestorhorariosescolares.materia.dominio;

public class Materia {
    private final int id;
    private final String clave;
    private final String nombre;
    private final int horasPracticas;
    private final int horasTeoricas;
    private final int idGrado;
    private boolean estatus;

    public Materia(int id, String clave, String nombre, int horasPracticas, int horasTeoricas, int idGrado, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
        this.idGrado = idGrado;
        this.estatus = estatus;
    }

    public Materia(String clave, String nombre, int horasPracticas, int horasTeoricas, int idGrado) {
        id = 0;
        estatus = true;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
        this.idGrado = idGrado;
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

    public int idGrado() {
        return idGrado;
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
