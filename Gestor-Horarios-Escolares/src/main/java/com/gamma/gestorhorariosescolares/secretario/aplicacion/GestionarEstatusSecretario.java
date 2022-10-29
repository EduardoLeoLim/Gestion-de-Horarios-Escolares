package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar.ServicioActualizadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;

import java.util.List;

public class GestionarEstatusSecretario {

    private final ServicioBuscador<Secretario> buscadorSecretario;
    private final ServicioActualizadorSecretario actualizadorSecretario;

    public GestionarEstatusSecretario(ServicioBuscador<Secretario> buscadorSecretario,
                                      ServicioActualizadorSecretario actualizadorSecretario) {
        this.buscadorSecretario = buscadorSecretario;
        this.actualizadorSecretario = actualizadorSecretario;
    }

    public void habilitarSecretario(int idSecretario) throws RecursoNoEncontradoException {
        List<Secretario> secretarios = buscadorSecretario.igual("id", String.valueOf(idSecretario)).buscar();
        if (secretarios.isEmpty())
            throw new RecursoNoEncontradoException("El secretario no se encuentra registrado en el sistema");

        Secretario secretario = secretarios.get(0);
        secretario.habilitar();
        actualizadorSecretario.actualizar(secretario);
    }

    public void deshabilitarSecretario(int idSecretario) throws RecursoNoEncontradoException {
        List<Secretario> secretarios = buscadorSecretario.igual("id", String.valueOf(idSecretario)).buscar();
        if (secretarios.isEmpty())
            throw new RecursoNoEncontradoException("El secretario no se encuentra registrado en el sistema");

        Secretario secretario = secretarios.get(0);
        secretario.deshabilitar();
        actualizadorSecretario.actualizar(secretario);
    }
}
