package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException() {
        super("El recurso no se encuentra registrado en el sistema.");
    }

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
