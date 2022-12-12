package com.gamma.gestorhorariosescolares.horario.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseGrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoInscripcionData;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;

import java.time.LocalTime;

public class HorarioData {
    private final int id;
    private final int diaSemana;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final GrupoInscripcionData grupo;
    private final ClaseGrupoData clase;


    public HorarioData(int id, int diaSemana, LocalTime horaInicio, LocalTime horaFin, GrupoInscripcionData grupo, ClaseGrupoData clase) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.grupo = grupo;
        this.clase = clase;
    }

    public static HorarioData fromAggregate(Horario horario, GrupoInscripcionData grupo, ClaseGrupoData clase) {
        return new HorarioData(horario.id(), horario.diaSemana(), horario.horaInicio(), horario.horaFin(), grupo, clase);
    }

    public int id() {
        return id;
    }

    public int diaSemana() {
        return diaSemana;
    }

    public LocalTime horaInicio() {
        return horaInicio;
    }

    public LocalTime horaFin() {
        return horaFin;
    }

    public GrupoInscripcionData grupo() {
        return grupo;
    }

    public ClaseGrupoData clase() {
        return clase;
    }
}
