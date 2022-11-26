package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.actualizar.ServicioActualizadorClase;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.AsignacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

import java.util.List;

public class AsignarMaestro {

    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Horario> buscadorHorario;
    private final ServicioActualizadorClase actualizadorClase;

    public AsignarMaestro(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Maestro> buscadorMaestro,
                          ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Horario> buscadorHorario,
                          ServicioActualizadorClase actualizadorClase) {
        this.buscadorClase = buscadorClase;
        this.buscadorMaestro = buscadorMaestro;
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorHorario = buscadorHorario;
        this.actualizadorClase = actualizadorClase;
    }

    public void asignar(int idClase, int idMaestro) throws RecursoNoEncontradoException, AsignacionInvalidaException {
        Clase clase = buscadorClase
                .igual("id", String.valueOf(idClase))
                .buscarPrimero();

        Maestro maestro = buscadorMaestro
                .igual("id", String.valueOf(idMaestro))
                .buscarPrimero();

        if (!maestro.estatus())
            throw new AsignacionInvalidaException("El maestro no está activo");

        Grupo grupo = buscadorGrupo.igual("id", String.valueOf(clase.idGrupo())).buscarPrimero();

        List<Horario> horariosClase = buscadorHorario
                .igual("idClase", String.valueOf(clase.id()))
                .buscar();

        List<Horario> horariosMaestro = buscadorHorario
                .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()))
                .igual("idMaestro", maestro.id().value().toString())
                .buscar();

        for (Horario horarioClase : horariosClase) {
            for (Horario horarioMaestro : horariosMaestro) {
                if (horarioMaestro.diaSemana() != horarioClase.diaSemana())
                    continue;

                if (horarioMaestro.horaInicio().compareTo(horarioClase.horaInicio()) >= 0 &&
                        horarioMaestro.horaInicio().compareTo(horarioClase.horaFin()) <= 0)
                    throw new AsignacionInvalidaException("El maestro ya tiene una clase asignada en ese horario");

                if (horarioMaestro.horaFin().compareTo(horarioClase.horaInicio()) >= 0 &&
                        horarioMaestro.horaFin().compareTo(horarioClase.horaFin()) <= 0)
                    throw new AsignacionInvalidaException("El maestro ya tiene una clase asignada en ese horario");
            }
        }

        clase.asignarIdMaestro(maestro.id().value());

        actualizadorClase.actualizar(clase);
    }

}