package com.gamma.gestorhorariosescolares.maestro.dominio;

public class Maestro {
    private final MaestroId id;
    private final String noPersonal;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final int idUsuario;
    private boolean estatus;

    public Maestro(MaestroId id, boolean estatus, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.estatus = estatus;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public Maestro(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        id = new MaestroId(0);
        estatus = true;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public MaestroId id() {
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
