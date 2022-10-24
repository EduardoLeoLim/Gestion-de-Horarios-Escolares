package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class ClaveDuplicadaException extends Exception {

    public ClaveDuplicadaException() {
        super("Ya hay un recurso registrado con esa clave");
    }

    public ClaveDuplicadaException(String mensaje) {
        super(mensaje);
    }

}