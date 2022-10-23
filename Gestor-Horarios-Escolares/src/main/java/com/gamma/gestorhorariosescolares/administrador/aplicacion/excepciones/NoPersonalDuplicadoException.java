package com.gamma.gestorhorariosescolares.administrador.aplicacion.excepciones;

public class NoPersonalDuplicadoException extends Exception {

    public NoPersonalDuplicadoException() {
        super("NoPersonal duplicado.");
    }

    public NoPersonalDuplicadoException(String mensaje) {
        super(mensaje);
    }

}