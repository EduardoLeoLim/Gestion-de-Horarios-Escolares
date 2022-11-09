package com.gamma.gestorhorariosescolares.grado.dominio;

public class Grado {
    private final int id;
    private final String clave;
    private final String nombre;
    private final int idPlanEstudio;
    private boolean estatus;

    public Grado(int id, String clave, String nombre, int idPlanEstudio, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.idPlanEstudio = idPlanEstudio;
        this.estatus = estatus;
    }

    public Grado(String clave, String nombre, int idPlanEstudio) {
        this.id = 0;
        this.estatus = true;
        this.clave = clave;
        this.nombre = nombre;
        this.idPlanEstudio = idPlanEstudio;
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

    public int idPlanEstudio() {
        return idPlanEstudio;
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
