package com.gamma.gestorhorariosescolares.edificio.dominio;

public class Edificio {
    private final int id;
    private final String clave;
    private final String nombre;
    private boolean estatus;

    public Edificio(int id, String clave, String nombre, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    public Edificio(String clave, String nombre) {
        id = 0;
        estatus = true;
        this.clave = clave;
        this.nombre = nombre;
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
