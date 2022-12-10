package com.gamma.gestorhorariosescolares.horario.aplicacion;

import java.util.List;

public class HorariosData {
    private final List<HorarioData> horarios;

    public HorariosData(List<HorarioData> horarios) {
        this.horarios = horarios;
    }

    public List<HorarioData> horarios() {
        return horarios;
    }
}
