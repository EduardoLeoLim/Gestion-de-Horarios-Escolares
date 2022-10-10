package com.gamma.gestorhorariosescolares.aplicacion.alumno.excepciones;

public class CurpDuplicadoException extends Exception{
    public CurpDuplicadoException(){
        super("CURP duplicado");
    }

    public CurpDuplicadoException(String mensaje){
        super(mensaje);
    }
}
