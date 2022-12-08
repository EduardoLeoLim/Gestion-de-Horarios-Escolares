package com.gamma.gestorhorariosescolares.horario.aplicacion;

import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;

public class HorarioDisponibleData {
    private final int diaSemana;
    private final String horaInicio;
    private final String horaFin;
    private final SalonData salon;
    private final PeriodoEscolarData periodoEscolar;

    public HorarioDisponibleData(int diaSemana, String horaInicio, String horaFin, SalonData salon, PeriodoEscolarData periodoEscolar) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.salon = salon;
        this.periodoEscolar = periodoEscolar;
    }

    public static HorarioDisponibleData fromAggregate(Horario horario, SalonData salon, PeriodoEscolarData periodoEscolar) {
        return new HorarioDisponibleData(horario.diaSemana(), horario.horaInicio().toString(), horario.horaFin().toString(), salon, periodoEscolar);
    }

    public int diaSemana() {
        return diaSemana;
    }

    public String horaInicio() {
        return horaInicio;
    }

    public String horaFin() {
        return horaFin;
    }

    public SalonData salon() {
        return salon;
    }

    public PeriodoEscolarData periodoEscolar() {
        return periodoEscolar;
    }
}
