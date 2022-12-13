package com.gamma.gestorhorariosescolares.horario.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseGrupoData;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoInscripcionData;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroClaseData;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaClaseData;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.util.ArrayList;
import java.util.List;

public class BuscarHorariosOcupados {
    private final ServicioBuscador<Horario> buscadorHorario;
    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;
    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Maestro> buscadorMaestro;

    public BuscarHorariosOcupados(ServicioBuscador<Horario> buscadorHorario,
                                  ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar,
                                  ServicioBuscador<Salon> buscadorSalon, ServicioBuscador<Grupo> buscadorGrupo,
                                  ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Materia> buscador,
                                  ServicioBuscador<Maestro> buscadorMaestro) {
        this.buscadorHorario = buscadorHorario;
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
        this.buscadorSalon = buscadorSalon;
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorClase = buscadorClase;
        this.buscadorMateria = buscador;
        this.buscadorMaestro = buscadorMaestro;
    }

    public HorariosData buscar(Integer idPeriodoEscolar, Integer idSalon, Integer idGrupo, Integer idClase)
            throws RecursoNoEncontradoException {
        PeriodoEscolar periodoEscolar = buscadorPeriodoEscolar
                .igual("id", String.valueOf(idPeriodoEscolar))
                .buscarPrimero();
        Salon salon = buscadorSalon
                .igual("id", String.valueOf(idSalon))
                .buscarPrimero();
        Grupo grupo = null;
        Clase clase = null;

        buscadorHorario.igual("idPeriodoEscolar", String.valueOf(periodoEscolar.id()));
        buscadorHorario.igual("idSalon", String.valueOf(salon.id()));
        if (idGrupo != null) {
            grupo = buscadorGrupo
                    .igual("id", String.valueOf(idGrupo))
                    .buscarPrimero();
            buscadorHorario.igual("idGrupo", String.valueOf(grupo.id()));
        }
        if (idClase != null) {
            clase = buscadorClase
                    .igual("id", String.valueOf(idClase))
                    .buscarPrimero();
            buscadorHorario.igual("idClase", String.valueOf(clase.id()));
        }
        List<Horario> horarios = buscadorHorario.buscar();

        return new HorariosData(prepararResultado(horarios));
    }

    private List<HorarioData> prepararResultado(List<Horario> horarios) {
        List<HorarioData> horariosData = new ArrayList<>();

        horarios.forEach(horario -> {
            try {
                GrupoInscripcionData grupoData = GrupoInscripcionData.fromAggregate(
                        buscadorGrupo.igual("id", String.valueOf(horario.idGrupo())).buscarPrimero()
                );
                MateriaClaseData materiaData = MateriaClaseData.fromAggregate(
                        buscadorMateria.igual("id", String.valueOf(horario.idClase())).buscarPrimero()
                );

                MaestroClaseData maestroData;
                try {
                    Maestro maestro = buscadorMaestro.igual("id", String.valueOf(horario.idClase())).buscarPrimero();
                    maestroData = MaestroClaseData.fromAggregate(maestro);
                } catch (RecursoNoEncontradoException e) {
                    maestroData = null;
                }

                ClaseGrupoData claseData = ClaseGrupoData.fromAggregate(
                        buscadorClase.igual("id", String.valueOf(horario.idClase())).buscarPrimero(),
                        materiaData, maestroData
                );

                HorarioData horarioData = HorarioData.fromAggregate(horario, grupoData, claseData);
                horariosData.add(horarioData);
            } catch (RecursoNoEncontradoException e) {
                throw new RuntimeException(e);
            }
        });

        return horariosData;
    }
}
