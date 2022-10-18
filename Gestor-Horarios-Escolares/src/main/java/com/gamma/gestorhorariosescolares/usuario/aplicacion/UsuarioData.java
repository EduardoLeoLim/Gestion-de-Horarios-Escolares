package com.gamma.gestorhorariosescolares.usuario.aplicacion;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class UsuarioData {

    private final Integer id;
    private final String tipo;
    private final String correoElectronico;
    private final String claveAcceso;

    public UsuarioData(int id, String correoElectronico, String claveAcceso, String tipo) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.claveAcceso = claveAcceso;
        this.tipo = tipo;
    }

    public static UsuarioData fromAggregate(Usuario usuario) {
        if (usuario == null)
            throw new NullPointerException();

        return new UsuarioData(usuario.id(), usuario.correoElectronico(), usuario.claveAcceso(), usuario.tipo());
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
