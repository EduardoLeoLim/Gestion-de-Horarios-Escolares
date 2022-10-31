package com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones;

public class FormatoInvalidoException extends Exception {

    private final String titulo;
    private final String descripcion;

    public FormatoInvalidoException() {
        super("El formato del dato es inválido.");
        descripcion = "El formato del dato es inválido.";
        titulo = "Dato con formato inválido";
    }

    public FormatoInvalidoException(String titulo, String descripcion) {
        super(descripcion);
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String titulo() {
        return titulo;
    }

    public String descripcion() {
        return descripcion;
    }
}