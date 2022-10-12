package com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones;

public class UsuarioDuplicadoException extends Exception {
    public UsuarioDuplicadoException() {
        super("Usuario duplicado");
    }

    public UsuarioDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
