package com.gamma.gestorhorariosescolares.clase.aplicacion;

import java.util.List;

public class ClasesGrupoData {

    private final List<ClaseGrupoData> clases;

    public ClasesGrupoData(List<ClaseGrupoData> clases) {
        this.clases = clases;
    }

    public List<ClaseGrupoData> clases() {
        return clases;
    }
}
