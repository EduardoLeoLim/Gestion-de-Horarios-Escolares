package com.gamma.gestorhorariosescolares.grupo.dominio;

public class Grupo {
    private final int id;
    private final String clave;
    private final String nombre;
    private final int idGrado;
    private final int[] idMaterias;
    private final int[] idInscripciones;
    private final int idPeriodoEscolar;

    public Grupo(int id, String clave, String nombre, int idGrado, int[] idMaterias, int[] idInscripciones, int idPeriodoEscolar) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.idGrado = idGrado;
        this.idMaterias = idMaterias;
        this.idInscripciones = idInscripciones;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public Grupo(String clave, String nombre, int idGrado, int[] idMaterias, int[] idInscripciones, int idPeriodoEscolar) {
        this.idInscripciones = idInscripciones;
        this.id = 0;
        this.clave = clave;
        this.nombre = nombre;
        this.idGrado = idGrado;
        this.idMaterias = idMaterias;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public int id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public String nombre() {
        return nombre;
    }

    public int idGrado() {
        return idGrado;
    }

    public int[] idMaterias() {
        return idMaterias;
    }

    public int idPeriodoEscolar() {
        return idPeriodoEscolar;
    }
}