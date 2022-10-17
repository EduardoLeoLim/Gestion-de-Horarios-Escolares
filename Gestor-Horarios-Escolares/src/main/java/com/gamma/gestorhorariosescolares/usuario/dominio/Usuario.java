package com.gamma.gestorhorariosescolares.usuario.dominio;

public class Usuario {
    private final int id;
    private final String tipo;
    private final String correoElectronico;
    private final String claveAcceso;

    public Usuario(int id, String correoElectronico, String claveAcceso, String tipo) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
        this.tipo = tipo;
    }

    public Usuario(String correoElectronico, String claveAcceso, String tipo) {
        this.id = 0;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
        this.tipo = tipo;
    }

    public int id() {
        return id;
    }

    public String correoElectronico() {
        return correoElectronico;
    }

    public String claveAcceso() {
        return claveAcceso;
    }

    public String tipo() {
        return tipo;
    }
}
