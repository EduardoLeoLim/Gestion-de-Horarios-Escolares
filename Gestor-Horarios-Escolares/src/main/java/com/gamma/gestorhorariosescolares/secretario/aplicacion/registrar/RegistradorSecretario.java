package com.gamma.gestorhorariosescolares.secretario.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;

public class RegistradorSecretario implements ServicioRegistradorSecretario {

    private final SecretarioRepositorio repositorio;

    public RegistradorSecretario(SecretarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        //Validar formato de datos
        Secretario secretario = new Secretario(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
        return repositorio.registrar(secretario);
    }

}