package com.gamma.gestorhorariosescolares.inscripcion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoInscripcionData;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoInscripcionData;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarInscripcionesPorGrupo {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Alumno> buscadorAlumno;

    public BuscarInscripcionesPorGrupo(ServicioBuscador<Grupo> buscadorGrupo,
                                       ServicioBuscador<Inscripcion> buscadorInscripcion,
                                       ServicioBuscador<Alumno> buscadorAlumno) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorAlumno = buscadorAlumno;
    }

    public InscripcionesGrupoData buscar(Integer idGrupo) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo.igual("id", idGrupo.toString()).buscarPrimero();

        if (grupo.idInscripciones().length == 0) {
            return new InscripcionesGrupoData(List.of());
        }

        for (int idInscripcion : grupo.idInscripciones()) {
            buscadorInscripcion.igual("id", String.valueOf(idInscripcion));
            if (grupo.idInscripciones().length > 1)
                buscadorInscripcion.esOpcional();
        }
        List<Inscripcion> inscripciones = buscadorInscripcion.buscar();

        List<InscripcionGrupoData> inscripcionesGrupo = inscripciones.stream().map(inscripcion -> {
            Alumno alumno;

            try {
                alumno = buscadorAlumno.igual("id", String.valueOf(inscripcion.idAlumno())).buscarPrimero();
            } catch (RecursoNoEncontradoException e) {
                alumno = new Alumno(inscripcion.idAlumno(), false, "", "", "", "", "", 0);
            }

            return InscripcionGrupoData.fromAggregate(inscripcion, AlumnoInscripcionData.fromAggregate(alumno),
                    GrupoInscripcionData.fromAggregate(grupo));
        }).collect(Collectors.toList());

        return new InscripcionesGrupoData(inscripcionesGrupo);
    }
}
