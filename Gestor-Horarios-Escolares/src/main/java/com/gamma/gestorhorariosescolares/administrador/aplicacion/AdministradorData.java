package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class AdministradorData {

    private final Integer id;
    private final String noPersonal;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final Boolean estatus;
    private final UsuarioData usuario;

    public AdministradorData(int id, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                             boolean estatus, UsuarioData usuario) {
        this.id = id;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.estatus = estatus;
        this.usuario = usuario;
    }

    public static AdministradorData fromAggregate(Administrador administrador, Usuario usuario) {
        if (administrador == null || usuario == null)
            throw new NullPointerException();

        UsuarioData usuarioData = UsuarioData.fromAggregate(usuario);
        return new AdministradorData(administrador.id(), administrador.noPersonal(), administrador.nombre(),
                administrador.apellidoPaterno(), administrador.apellidoMaterno(), administrador.estatus(), usuarioData);
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

    public boolean estatus() {
        return estatus;
    }

    public UsuarioData usuario() {
        return usuario;
    }
}
