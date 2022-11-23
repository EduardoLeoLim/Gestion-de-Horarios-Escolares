package com.gamma.gestorhorariosescolares.horario.aplicacion.registrar;

import java.time.LocalTime;

public interface ServicioRegistradorHorario {
    void registrar(int diaSemana, LocalTime horaInicio, LocalTime horaFin, int idClase, int idSalon);
}
