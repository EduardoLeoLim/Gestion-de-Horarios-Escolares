package com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public interface ServicioActualizadorUsuario {
    void actualizar(Usuario usuario) throws FormatoInvalidoException;
}
