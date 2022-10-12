package com.gamma.gestorhorariosescolares.alumno.dominio;

public class Alumno {
    private final int id;
    private final String matricula;
    private final String curp;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private boolean estatus;
    private final int idUsuario;

    public Alumno(int id, boolean estatus, String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.estatus = estatus;
        this.matricula = matricula;
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public Alumno(String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        id = 0;
        estatus = true;
        this.matricula = matricula;
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public int id() {
        return id;
    }

    public String matricula() {
        return matricula;
    }

    public String curp() {
        return curp;
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

    public boolean estaHabilitado() {
        return estatus;
    }

    public int idUsuario() {
        return idUsuario;
    }
}
