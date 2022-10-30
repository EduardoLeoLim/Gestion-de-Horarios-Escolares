package com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;

public interface ServicioRegistradorUsuario {
    int registrar(String telefono, String correoElectronico, String claveAcceso, String tipo) throws FormatoInvalidoException;
}
