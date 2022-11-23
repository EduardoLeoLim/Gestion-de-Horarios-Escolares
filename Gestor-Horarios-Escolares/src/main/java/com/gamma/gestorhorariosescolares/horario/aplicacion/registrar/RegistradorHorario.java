package com.gamma.gestorhorariosescolares.horario.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.horario.dominio.HorarioRepositorio;

import java.time.LocalTime;

public class RegistradorHorario implements ServicioRegistradorHorario {

    private final HorarioRepositorio repositorio;

    public RegistradorHorario(HorarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void registrar(int diaSemana, LocalTime horaInicio, LocalTime horaFin, int idClase, int idSalon) {
        Horario horario = new Horario(diaSemana, horaInicio, horaFin, idClase, idSalon);
        repositorio.registrar(horario);
    }

}