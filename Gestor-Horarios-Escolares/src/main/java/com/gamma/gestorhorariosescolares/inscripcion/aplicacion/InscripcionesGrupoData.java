package com.gamma.gestorhorariosescolares.inscripcion.aplicacion;

import java.util.List;

public class InscripcionesGrupoData {

    private final List<InscripcionGrupoData> inscripciones;

    public InscripcionesGrupoData(List<InscripcionGrupoData> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public List<InscripcionGrupoData> inscripciones() {
        return inscripciones;
    }
}
