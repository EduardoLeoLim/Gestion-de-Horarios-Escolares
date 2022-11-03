package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar.ServicioActualizadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;

public class GestionarEstatusSecretario {

    private final ServicioBuscador<Secretario> buscadorSecretario;
    private final ServicioActualizadorSecretario actualizadorSecretario;

    public GestionarEstatusSecretario(ServicioBuscador<Secretario> buscadorSecretario,
                                      ServicioActualizadorSecretario actualizadorSecretario) {
        this.buscadorSecretario = buscadorSecretario;
        this.actualizadorSecretario = actualizadorSecretario;
    }

    public void habilitarSecretario(int idSecretario) throws RecursoNoEncontradoException {
        Secretario secretario = buscadorSecretario
                .igual("id", String.valueOf(idSecretario))
                .buscarPrimero();
        secretario.habilitar();
        actualizadorSecretario.actualizar(secretario);
    }

    public void deshabilitarSecretario(int idSecretario) throws RecursoNoEncontradoException {
        Secretario secretario = buscadorSecretario
                .igual("id", String.valueOf(idSecretario))
                .buscarPrimero();
        secretario.deshabilitar();
        actualizadorSecretario.actualizar(secretario);
    }
}
