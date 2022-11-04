package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.actualizar.ServicioActualizadorMateria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class GestionarEstatusMateria {

    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioActualizadorMateria actualizadorMateria;

    public GestionarEstatusMateria(ServicioBuscador<Materia> buscadorMateria,
                                   ServicioActualizadorMateria actualizadorMateria) {
        this.buscadorMateria = buscadorMateria;
        this.actualizadorMateria = actualizadorMateria;
    }

    public void habilitarMateria(int idMateria) throws RecursoNoEncontradoException {
        Materia materia = buscadorMateria
                .igual("id", String.valueOf(idMateria))
                .buscarPrimero();
        materia.habilitar();
        actualizadorMateria.actualizar(materia);
    }

    public void deshabilitarMateria(int idMateria) throws RecursoNoEncontradoException {
        Materia materia = buscadorMateria
                .igual("id", String.valueOf(idMateria))
                .buscarPrimero();
        materia.deshabilitar();
        actualizadorMateria.actualizar(materia);
    }

}