package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.AsignacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar.ServicioActualizadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

import java.util.List;

public class RemoverInscripcion {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Evaluacion> buscadorEvaluacion;
    private final ServicioActualizadorGrupo actualizadorGrupo;

    public RemoverInscripcion(ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Inscripcion> buscadorInscripcion,
                              ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Evaluacion> buscadorEvaluacion,
                              ServicioActualizadorGrupo actualizadorGrupo) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorClase = buscadorClase;
        this.buscadorEvaluacion = buscadorEvaluacion;
        this.actualizadorGrupo = actualizadorGrupo;
    }

    public void remover(int idGrupo, int idInscripcion) throws RecursoNoEncontradoException, AsignacionInvalidaException {
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(idGrupo))
                .buscarPrimero();
        Inscripcion inscripcion = buscadorInscripcion
                .igual("id", String.valueOf(idInscripcion))
                .buscarPrimero();

        if (!grupo.estaInscrito(inscripcion.id()))
            throw new AsignacionInvalidaException("La inscripción no está asignada al grupo");

        List<Clase> clases = buscadorClase
                .igual("idGrupo", String.valueOf(grupo.id()))
                .buscar();
        buscadorEvaluacion.igual("idAlumno", String.valueOf(inscripcion.idAlumno()));
        clases.forEach(clase -> {
            buscadorEvaluacion.igual("idMateria", String.valueOf(clase.idMateria()));
            if (clases.size() > 1)
                buscadorEvaluacion.esOpcional();
        });
        List<Evaluacion> evaluaciones = buscadorEvaluacion.buscar();
        if (!evaluaciones.isEmpty())
            throw new AsignacionInvalidaException("El alumno tiene evaluaciones asignadas a las clases del grupo");

        grupo.removerInscripcion(inscripcion.id());

        actualizadorGrupo.actualizar(grupo);
    }

}