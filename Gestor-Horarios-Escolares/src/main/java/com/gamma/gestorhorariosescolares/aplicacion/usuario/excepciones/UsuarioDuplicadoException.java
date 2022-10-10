package com.gamma.gestorhorariosescolares.aplicacion.usuario.excepciones;

public class UsuarioDuplicadoException extends Exception{
    public UsuarioDuplicadoException(){
        super("Usuario duplicado");
    }

    public UsuarioDuplicadoException(String mensaje){
        super(mensaje);
    }
}
