package com.gamma.gestorhorariosescolares.inscripcion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoInscripcionData;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarInscripcionesSinAsignar {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Alumno> buscadorAlumno;

    public BuscarInscripcionesSinAsignar(ServicioBuscador<Grupo> buscadorGrupo,
                                         ServicioBuscador<Inscripcion> buscadorInscripcion,
                                         ServicioBuscador<Alumno> buscadorAlumno) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorAlumno = buscadorAlumno;
    }

    public InscripcionesGrupoData buscar(Integer idGrupo) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo
                .igual("id", idGrupo.toString())
                .buscarPrimero();

        List<Grupo> grupos = buscadorGrupo
                .igual("idGrado", String.valueOf(grupo.idGrado()))
                .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()))
                .buscar();

        buscadorInscripcion.igual("idGrado", String.valueOf(grupo.idGrado()));
        buscadorInscripcion.igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()));
        grupos.forEach(grupoEncontrado -> {
            for (int idInscripcion : grupoEncontrado.idInscripciones()) {
                buscadorInscripcion.diferente("id", String.valueOf(idInscripcion));
            }
        });
        List<Inscripcion> inscripciones = buscadorInscripcion.buscar();

        List<InscripcionGrupoData> inscripcionesGrupo = inscripciones.stream().map(inscripcion -> {
            Alumno alumno;

            try {
                alumno = buscadorAlumno.igual("id", String.valueOf(inscripcion.idAlumno())).buscarPrimero();
            } catch (RecursoNoEncontradoException e) {
                alumno = new Alumno(inscripcion.idAlumno(), false, "", "", "", "", "", 0);
            }

            return InscripcionGrupoData.fromAggregate(inscripcion, AlumnoInscripcionData.fromAggregate(alumno), null);
        }).collect(Collectors.toList());

        return new InscripcionesGrupoData(inscripcionesGrupo);
    }

    public InscripcionesGrupoData buscar(Integer idGrupo, String criterio) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo
                .igual("id", idGrupo.toString())
                .buscarPrimero();

        List<Grupo> grupos = buscadorGrupo
                .igual("idGrado", String.valueOf(grupo.idGrado()))
                .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()))
                .buscar();

        buscadorInscripcion.igual("idGrado", String.valueOf(grupo.idGrado()));
        buscadorInscripcion.igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()));
        grupos.forEach(grupoEncontrado -> {
            for (int idInscripcion : grupoEncontrado.idInscripciones()) {
                buscadorInscripcion.diferente("id", String.valueOf(idInscripcion));
            }
        });
        List<Inscripcion> inscripciones = buscadorInscripcion.buscar();

        List<InscripcionGrupoData> inscripcionesGrupo = new ArrayList<>();

        inscripciones.forEach(inscripcion -> {
            try {
                Alumno alumno = buscadorAlumno.igual("id", String.valueOf(inscripcion.idAlumno())).buscarPrimero();
                if (alumno.nombre().toLowerCase().contains(criterio.toLowerCase()) ||
                        alumno.apellidoPaterno().toLowerCase().contains(criterio.toLowerCase()) ||
                        alumno.apellidoMaterno().toLowerCase().contains(criterio.toLowerCase()) ||
                        alumno.matricula().toLowerCase().contains(criterio.toLowerCase()) ||
                        alumno.curp().toLowerCase().contains(criterio.toLowerCase())) {
                    inscripcionesGrupo.add(InscripcionGrupoData.fromAggregate(inscripcion, AlumnoInscripcionData.fromAggregate(alumno), null));
                }
            } catch (RecursoNoEncontradoException ignored) {
            }
        });

        return new InscripcionesGrupoData(inscripcionesGrupo);
    }
}
