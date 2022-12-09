package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class RegistroHorarioInvalidoException extends Exception {

    public RegistroHorarioInvalidoException() {
        super("No es posible registrar el horario");
    }

    public RegistroHorarioInvalidoException(String mensaje) {
        super(mensaje);
    }

}
