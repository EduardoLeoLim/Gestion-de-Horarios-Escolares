package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar.ServicioActualizadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

public class GestionarInscripcionesGrupo {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioActualizadorGrupo actualizadorGrupo;

    public GestionarInscripcionesGrupo(ServicioBuscador<Grupo> buscadorGrupo,
                                       ServicioBuscador<Inscripcion> buscadorInscripcion,
                                       ServicioActualizadorGrupo actualizadorGrupo) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.actualizadorGrupo = actualizadorGrupo;
    }

    public void agregarInscripcion(int idGrupo, int idInscripcion) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(idGrupo))
                .buscarPrimero();
        Inscripcion inscripcion = buscadorInscripcion
                .igual("id", String.valueOf(idInscripcion))
                .buscarPrimero();

        grupo.agregarInscripcion(inscripcion.id());

        actualizadorGrupo.actualizar(grupo);
    }

    public void removerInscripcion(int idGrupo, int idInscripcion) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(idGrupo))
                .buscarPrimero();
        Inscripcion inscripcion = buscadorInscripcion
                .igual("id", String.valueOf(idInscripcion))
                .buscarPrimero();

        grupo.removerInscripcion(inscripcion.id());

        actualizadorGrupo.actualizar(grupo);
    }

}
