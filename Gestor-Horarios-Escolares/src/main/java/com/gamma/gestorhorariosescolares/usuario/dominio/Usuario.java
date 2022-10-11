package com.gamma.gestorhorariosescolares.usuario.dominio;

public class Usuario {
    private final int id;
    private final String correoElectronico;
    private final String claveAcceso;

    public Usuario(int id, String correoElectronico, String claveAcceso){
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
    }

    public Usuario(String correoElectronico, String claveAcceso){
        this.id = 0;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
    }
}
