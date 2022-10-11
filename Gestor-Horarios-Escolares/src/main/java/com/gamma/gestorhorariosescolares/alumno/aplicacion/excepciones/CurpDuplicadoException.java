package com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones;

public class CurpDuplicadoException extends Exception{
    public CurpDuplicadoException(){
        super("CURP duplicado");
    }

    public CurpDuplicadoException(String mensaje){
        super(mensaje);
    }
}
