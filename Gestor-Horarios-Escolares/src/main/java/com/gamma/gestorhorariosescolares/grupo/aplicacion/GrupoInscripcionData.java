package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;

public class GrupoInscripcionData {
    private final int id;
    private final String clave;
    private final String nombre;

    public GrupoInscripcionData(int id, String clave, String nombre) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
    }

    public static GrupoInscripcionData fromAggregate(Grupo grupo) {
        return new GrupoInscripcionData(grupo.id(), grupo.clave(), grupo.nombre());
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
}
