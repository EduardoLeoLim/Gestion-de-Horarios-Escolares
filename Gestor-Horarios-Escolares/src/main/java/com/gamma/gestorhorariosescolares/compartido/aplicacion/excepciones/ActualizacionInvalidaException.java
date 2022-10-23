package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class ActualizacionInvalidaException extends Exception {
    public ActualizacionInvalidaException() {
        super("No es posible actualizar los datos del recurso, porque ahora estos son inválidos");
    }

    public ActualizacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
