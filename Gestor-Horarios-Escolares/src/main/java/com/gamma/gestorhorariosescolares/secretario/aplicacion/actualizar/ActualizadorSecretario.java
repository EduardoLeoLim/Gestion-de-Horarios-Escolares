package com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;

public class ActualizadorSecretario implements ServicioActualizadorSecretario {

    private final SecretarioRepositorio repositorio;

    public ActualizadorSecretario(SecretarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Secretario secretario) {
        //Validar formato de datos
        repositorio.actualizar(secretario);
    }
}
