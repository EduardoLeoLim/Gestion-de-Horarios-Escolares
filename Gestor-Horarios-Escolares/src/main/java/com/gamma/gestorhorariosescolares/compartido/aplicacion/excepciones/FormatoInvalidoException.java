package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class FormatoInvalidoException extends Exception {

    public FormatoInvalidoException() {
        super("El formato del dato es inválido.");
    }

    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}