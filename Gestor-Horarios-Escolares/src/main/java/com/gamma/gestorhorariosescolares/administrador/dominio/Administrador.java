package com.gamma.gestorhorariosescolares.administrador.dominio;

public class Administrador {
    private final int id;
    private String noPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private boolean estatus;
    private final int idUsuario;

    public Administrador(int id, boolean estatus, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.estatus = estatus;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public Administrador(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
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

    public void habilitar(){
        estatus = true;
    }

    public void deshabilitar(){
        estatus = false;
    }

    public boolean estaHabilitado(){
        return estatus;
    }

    public int idUsuario(){
        return idUsuario;
    }
}
