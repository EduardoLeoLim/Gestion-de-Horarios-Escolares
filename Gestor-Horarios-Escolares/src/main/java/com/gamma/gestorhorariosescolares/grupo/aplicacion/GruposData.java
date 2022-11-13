package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import java.util.List;

public class GruposData {

    private final List<GrupoData> grupos;

    public GruposData(List<GrupoData> grupos) {
        this.grupos = grupos;
    }

    public List<GrupoData> grupos() {
        return grupos;
    }

}
