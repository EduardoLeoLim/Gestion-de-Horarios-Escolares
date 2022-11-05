package com.gamma.gestorhorariosescolares.materia.aplicacion.excepciones;

public class SuperposicionRangoFechasException extends Exception {

    public SuperposicionRangoFechasException() {
        super("Las fechas se superponen.");
    }

    public SuperposicionRangoFechasException(String mensaje) {
        super(mensaje);
    }
}
