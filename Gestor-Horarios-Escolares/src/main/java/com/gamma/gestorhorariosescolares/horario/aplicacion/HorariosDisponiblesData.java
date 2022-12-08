package com.gamma.gestorhorariosescolares.horario.aplicacion;

import java.util.List;

public class HorariosDisponiblesData {
    private final List<HorarioDisponibleData> horarios;

    public HorariosDisponiblesData(List<HorarioDisponibleData> horarios) {
        this.horarios = horarios;
    }

    public List<HorarioDisponibleData> horarios() {
        return horarios;
    }
}
