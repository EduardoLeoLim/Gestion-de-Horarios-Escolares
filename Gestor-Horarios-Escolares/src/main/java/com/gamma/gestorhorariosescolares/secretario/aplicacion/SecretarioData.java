package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class SecretarioData {
    private final Integer id;
    private final String noPersonal;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final Boolean estatus;
    private final UsuarioData usuario;

    public SecretarioData(int id, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                          boolean estatus, UsuarioData usuario) {
        this.id = id;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.estatus = estatus;
        this.usuario = usuario;
    }

    public static SecretarioData fromAggregate(Secretario secretario, Usuario usuario) {
        if (secretario == null || usuario == null)
            throw new NullPointerException();

        UsuarioData usuarioData = UsuarioData.fromAggregate(usuario);
        return new SecretarioData(secretario.id(), secretario.noPersonal(), secretario.nombre(),
                secretario.apellidoPaterno(), secretario.apellidoMaterno(), secretario.estatus(), usuarioData);
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
