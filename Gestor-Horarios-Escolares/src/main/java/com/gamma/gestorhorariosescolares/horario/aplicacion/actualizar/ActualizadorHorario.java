package com.gamma.gestorhorariosescolares.horario.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.horario.dominio.HorarioRepositorio;

public class ActualizadorHorario implements ServicioActualizadorHorario {

    private final HorarioRepositorio repositorio;

    public ActualizadorHorario(HorarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Horario horario) {
        repositorio.actualizar(horario);
    }

}