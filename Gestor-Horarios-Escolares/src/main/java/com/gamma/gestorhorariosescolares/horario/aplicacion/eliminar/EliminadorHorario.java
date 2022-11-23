package com.gamma.gestorhorariosescolares.horario.aplicacion.eliminar;

import com.gamma.gestorhorariosescolares.horario.dominio.HorarioRepositorio;

public class EliminadorHorario implements ServicioEliminadorHorario {

    private final HorarioRepositorio repositorio;

    public EliminadorHorario(HorarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void eliminar(int idHorario) {
        repositorio.eliminar(idHorario);
    }
}
