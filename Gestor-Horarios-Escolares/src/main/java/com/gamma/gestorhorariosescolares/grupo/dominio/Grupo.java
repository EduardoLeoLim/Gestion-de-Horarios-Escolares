package com.gamma.gestorhorariosescolares.grupo.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.IntArrayUtils;

public class Grupo {
    private final int id;
    private final String clave;
    private final String nombre;
    private final int idGrado;
    private final int idPeriodoEscolar;
    private int[] idInscripciones;

    public Grupo(int id, String clave, String nombre, int idGrado, int[] idInscripciones, int idPeriodoEscolar) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.idGrado = idGrado;
        this.idInscripciones = idInscripciones;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public Grupo(String clave, String nombre, int idGrado, int idPeriodoEscolar) {
        this.id = 0;
        this.clave = clave;
        this.nombre = nombre;
        this.idGrado = idGrado;
        this.idInscripciones = new int[0];
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

    public int[] idInscripciones() {
        return idInscripciones;
    }

    public void agregarInscripcion(int idInscripcion) {
        if (IntArrayUtils.contains(idInscripciones, idInscripcion))
            return;
        idInscripciones = IntArrayUtils.add(idInscripciones, idInscripcion);
    }

    public void removerInscripcion(int idInscripcion) {
        if (!IntArrayUtils.contains(idInscripciones, idInscripcion))
            return;
        idInscripciones = IntArrayUtils.remove(idInscripciones, idInscripcion);
    }

    public int idPeriodoEscolar() {
        return idPeriodoEscolar;
    }
}