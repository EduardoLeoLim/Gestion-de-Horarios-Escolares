package com.gamma.gestorhorariosescolares.secretario.dominio;

public class Secretario {
    private final int id;
    private final String noPersonal;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private boolean estatus;
    private final int idUsuario;

    public Secretario(int id, boolean estatus, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.estatus = estatus;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public Secretario(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        id = 0;
        estatus = true;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public int id() {
        return id;
    }

    public String noPersonal() {
        return noPersonal;
    }

    public String nombre() {
        return nombre;
    }

    public String apellidoPaterno() {
        return apellidoPaterno;
    }

    public String apellidoMaterno() {
        return apellidoMaterno;
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

    public int idUsuario() {
        return idUsuario;
    }
}
