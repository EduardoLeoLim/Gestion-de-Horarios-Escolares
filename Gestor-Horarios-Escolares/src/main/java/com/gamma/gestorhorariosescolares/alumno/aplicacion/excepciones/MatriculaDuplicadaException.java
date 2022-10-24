package com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones;

public class MatriculaDuplicadaException extends Exception{

    public MatriculaDuplicadaException() {
        super("Matrícula duplicada");
    }

    public MatriculaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
