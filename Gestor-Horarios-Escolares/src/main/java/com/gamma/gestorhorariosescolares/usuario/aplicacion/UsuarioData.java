package com.gamma.gestorhorariosescolares.usuario.aplicacion;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class UsuarioData {

    private final Integer id;
    private final String telefono;
    private final String tipo;
    private final String correoElectronico;
    private final String claveAcceso;

    public UsuarioData(int id, String telefono, String correoElectronico, String claveAcceso, String tipo) {
        this.id = id;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
        this.tipo = tipo;
    }

    public static UsuarioData fromAggregate(Usuario usuario) {
        if (usuario == null)
            throw new NullPointerException();

        return new UsuarioData(usuario.id(), usuario.telefono(), usuario.correoElectronico(), usuario.claveAcceso(), usuario.tipo());
    }

    public Integer id() {
        return id;
    }

    public String telefono() {
        return telefono;
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
