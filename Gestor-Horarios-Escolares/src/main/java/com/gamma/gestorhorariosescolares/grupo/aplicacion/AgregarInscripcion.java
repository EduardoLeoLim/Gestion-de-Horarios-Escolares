package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.AsignacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar.ServicioActualizadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

import java.util.List;

public class AgregarInscripcion {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioActualizadorGrupo actualizadorGrupo;

    public AgregarInscripcion(ServicioBuscador<Grupo> buscadorGrupo,
                              ServicioBuscador<Inscripcion> buscadorInscripcion,
                              ServicioActualizadorGrupo actualizadorGrupo) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.actualizadorGrupo = actualizadorGrupo;
    }

    public void agregar(int idGrupo, int idInscripcion) throws RecursoNoEncontradoException, AsignacionInvalidaException {
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(idGrupo))
                .buscarPrimero();
        Inscripcion inscripcion = buscadorInscripcion
                .igual("id", String.valueOf(idInscripcion))
                .buscarPrimero();

        //¿La inscripción se puede agregar al grupo?
        if (!(inscripcion.idGrado() == grupo.idGrado() && inscripcion.idPeriodoEscolar() == grupo.idPeriodoEscolar()))
            throw new AsignacionInvalidaException("La inscripción no pertenece al grado del grupo");

        //¿Está inscrito en un grupo?
        List<Grupo> grupos = buscadorGrupo
                .igual("idPeriodoEscolar", String.valueOf(grupo.idPeriodoEscolar()))
                .igual("idGrado", String.valueOf(grupo.idGrado()))
                .buscar();
        if (grupos.stream().anyMatch(grupoEncontrado -> grupoEncontrado.estaInscrito(idInscripcion)))
            throw new AsignacionInvalidaException("La inscripción ya está asignada en un grupo");

        grupo.agregarInscripcion(inscripcion.id());

        actualizadorGrupo.actualizar(grupo);
    }

}
