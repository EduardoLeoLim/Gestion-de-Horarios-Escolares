package com.gamma.gestorhorariosescolares.horario.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RegistroHorarioInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.horario.aplicacion.registrar.ServicioRegistradorHorario;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.time.LocalTime;
import java.util.List;

public class RegistrarHorario {

    private final ServicioBuscador<Horario> buscadorHorario;
    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioRegistradorHorario registradorHorario;

    public RegistrarHorario(ServicioBuscador<Horario> buscadorHorario, ServicioBuscador<Clase> buscadorClase,
                            ServicioBuscador<Salon> buscadorSalon, ServicioBuscador<Grupo> buscadorGrupo,
                            ServicioRegistradorHorario registradorHorario) {
        this.buscadorHorario = buscadorHorario;
        this.buscadorClase = buscadorClase;
        this.buscadorSalon = buscadorSalon;
        this.buscadorGrupo = buscadorGrupo;
        this.registradorHorario = registradorHorario;
    }

    public void registrar(Integer diaSemana, LocalTime horaInicio, LocalTime horaFin, Integer idClase, Integer idSalon)
            throws RecursoNoEncontradoException, RegistroHorarioInvalidoException {
        Salon salon = buscadorSalon
                .igual("id", idSalon.toString())
                .buscarPrimero();
        Clase clase = buscadorClase
                .igual("id", idClase.toString())
                .buscarPrimero();
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(clase.idGrupo()))
                .buscarPrimero();

        List<Horario> horariosSalon = buscadorHorario
                .igual("idSalon", idSalon.toString()).esObligatorio()
                .igual("diaSemana", diaSemana.toString()).esObligatorio()
                .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar())).esObligatorio()
                .buscar();

        for (Horario horario : horariosSalon) {
            if (horario.horaInicio().compareTo(horaInicio) >= 0 && horario.horaFin().compareTo(horaInicio) <= 0) {
                throw new RegistroHorarioInvalidoException("El horario del salon " + salon.clave() + " esta ocupado");
            }
            if (horario.horaInicio().compareTo(horaFin) >= 0 && horario.horaFin().compareTo(horaFin) <= 0) {
                throw new RegistroHorarioInvalidoException("El horario del salon " + salon.clave() + " esta ocupado");
            }
        }

        if (clase.idMaestro() != null) {
            List<Horario> horariosMaestro = buscadorHorario
                    .igual("idMaestro", clase.idMaestro().value().toString()).esObligatorio()
                    .igual("diaSemana", diaSemana.toString()).esObligatorio()
                    .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar())).esObligatorio()
                    .buscar();

            for (Horario horario : horariosMaestro) {
                if (horario.horaInicio().compareTo(horaInicio) >= 0 && horario.horaFin().compareTo(horaInicio) <= 0) {
                    throw new RegistroHorarioInvalidoException("El maestro asignado a esta clase ya tiene otra clase asignada en este horario");
                }
                if (horario.horaInicio().compareTo(horaFin) >= 0 && horario.horaFin().compareTo(horaFin) <= 0) {
                    throw new RegistroHorarioInvalidoException("El maestro asignado a esta clase ya tiene otra clase asignada en este horario");
                }
            }
        }

        registradorHorario.registrar(diaSemana, horaInicio, horaFin, clase.id().value(), salon.id());
    }
}
