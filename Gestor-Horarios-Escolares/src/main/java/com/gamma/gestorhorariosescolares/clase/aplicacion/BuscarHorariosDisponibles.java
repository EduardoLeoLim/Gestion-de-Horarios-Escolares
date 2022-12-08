package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorarioDisponibleData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorariosDisponiblesData;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuscarHorariosDisponibles {
    private final ServicioBuscador<Horario> buscadorHorario;
    private final ServicioBuscador<Edificio> buscadorEdificio;
    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;

    public BuscarHorariosDisponibles(ServicioBuscador<Horario> buscadorHorario, ServicioBuscador<Edificio> buscadorEdificio,
                                     ServicioBuscador<Salon> buscadorSalon, ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar) {
        this.buscadorHorario = buscadorHorario;
        this.buscadorEdificio = buscadorEdificio;
        this.buscadorSalon = buscadorSalon;
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;

    }

    public HorariosDisponiblesData buscar(Integer idPeriodoEscolar, Integer idSalon) throws RecursoNoEncontradoException {
        PeriodoEscolar periodoEscolar = buscadorPeriodoEscolar
                .igual("id", String.valueOf(idPeriodoEscolar))
                .buscarPrimero();
        Salon salon = buscadorSalon
                .igual("id", String.valueOf(idSalon))
                .buscarPrimero();
        Edificio edificio = buscadorEdificio
                .igual("id", String.valueOf(salon.idEdificio()))
                .buscarPrimero();

        List<Horario> horarios = buscadorHorario
                .igual("idPeriodoEscolar", String.valueOf(idPeriodoEscolar))
                .igual("idSalon", String.valueOf(idSalon))
                .buscar();

        List<Horario> horariosDisponibles = generarListaHorarios(periodoEscolar, salon, horarios);

        return new HorariosDisponiblesData(horariosDisponibles.stream().map(horario -> HorarioDisponibleData.fromAggregate(horario,
                        SalonData.fromAggregate(salon, edificio), PeriodoEscolarData.fromAggregate(periodoEscolar)))
                .collect(Collectors.toList()));
    }

    private List<Horario> generarListaHorarios(PeriodoEscolar periodoEscolar, Salon salon, List<Horario> horariosOcupados) {
        List<Horario> horariosDisponibles = new ArrayList<>();

        List<Integer> diasSemana = List.of(0, 1, 2, 3, 4);
        List<LocalTime> horasInicio = IntStream.range(7, 22)
                .mapToObj(i -> LocalTime.of(i, 0))
                .collect(Collectors.toList());

        diasSemana.forEach(diaSemana -> {
            horasInicio.forEach(horaInicio -> {
                LocalTime horaFin = horaInicio.plusMinutes(59);
                Horario horario = new Horario(0, diaSemana, horaInicio, horaFin, 0, 0, 0, null,
                        salon.idEdificio(), salon.id(), periodoEscolar.id());

                boolean estaOcupado = horariosOcupados.stream()
                        .filter(horarioOcupado -> horarioOcupado.idPeriodoEscolar() == horario.idPeriodoEscolar())
                        .filter(horarioOcupado -> horarioOcupado.idSalon() == horario.idSalon())
                        .filter(horarioOcupado -> horarioOcupado.diaSemana() == horario.diaSemana())
                        .filter(horarioOcupado -> horarioOcupado.horaInicio().compareTo(horario.horaInicio()) >= 0
                                && horarioOcupado.horaInicio().compareTo(horario.horaFin()) <= 0)
                        .filter(horarioOcupado -> horarioOcupado.horaFin().compareTo(horario.horaInicio()) >= 0
                                && horarioOcupado.horaFin().compareTo(horario.horaFin()) <= 0)
                        .findAny().isPresent();

                if (!estaOcupado)
                    horariosDisponibles.add(horario);
            });
        });

        return horariosDisponibles;
    }

}
