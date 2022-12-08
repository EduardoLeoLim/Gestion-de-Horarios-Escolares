package com.gamma.gestorhorariosescolares.horario.dominio;

import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroId;

import java.time.LocalTime;

public class Horario {
    private final int id;
    private final int diaSemana;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final int idMateria;
    private final int idGrupo;
    private final int idClase;
    private final MaestroId idMaestro;
    private final int idEdificio;
    private final int idSalon;
    private final int idPeriodoEscolar;

    public Horario(int id, int diaSemana, LocalTime horaInicio, LocalTime horaFin, int idMateria, int idGrupo, int idClase, MaestroId idMaestro, int idEdificio, int idSalon, int idPeriodoEscolar) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMateria = idMateria;
        this.idGrupo = idGrupo;
        this.idClase = idClase;
        this.idMaestro = idMaestro;
        this.idEdificio = idEdificio;
        this.idSalon = idSalon;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public Horario(int diaSemana, LocalTime horaInicio, LocalTime horaFin, int idClase, int idSalon) {
        this.id = 0;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMateria = 0;
        this.idGrupo = 0;
        this.idClase = idClase;
        this.idMaestro = null;
        this.idEdificio = 0;
        this.idSalon = idSalon;
        this.idPeriodoEscolar = 0;
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

    public int idMateria() {
        return idMateria;
    }

    public int idGrupo() {
        return idGrupo;
    }

    public int idClase() {
        return idClase;
    }

    public MaestroId idMaestro() {
        return idMaestro;
    }

    public int idEdificio() {
        return idEdificio;
    }

    public int idSalon() {
        return idSalon;
    }

    public int idPeriodoEscolar() {
        return idPeriodoEscolar;
    }
}