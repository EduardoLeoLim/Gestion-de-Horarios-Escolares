package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import java.util.List;

public class MaestrosData {
    private final List<MaestroData> maestros;

    public MaestrosData(List<MaestroData> maestros) {
        this.maestros = maestros;
    }

    public List<MaestroData> maestros() {
        return maestros;
    }
}
