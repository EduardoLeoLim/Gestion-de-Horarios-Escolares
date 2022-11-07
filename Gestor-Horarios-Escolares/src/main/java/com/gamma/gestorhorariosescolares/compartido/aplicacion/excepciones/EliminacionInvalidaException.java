package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class EliminacionInvalidaException extends Exception {

    public EliminacionInvalidaException() {
        super("El recurso no puede ser eliminado en este momento");
    }

    public EliminacionInvalidaException(String mensaje) {
        super(mensaje);
    }

}