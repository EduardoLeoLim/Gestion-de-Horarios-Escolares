package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar.ActualizadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;

public class GestionarEstatusAlumno {

    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ActualizadorAlumno actualizadorAlumno;

    public GestionarEstatusAlumno(ServicioBuscador<Alumno> buscadorAlumno, ActualizadorAlumno actualizadorAlumno) {
        this.buscadorAlumno = buscadorAlumno;
        this.actualizadorAlumno = actualizadorAlumno;
    }

    public void habilitarAlumno(int idAlumno) throws RecursoNoEncontradoException {
        Alumno alumno = buscadorAlumno
                .igual("id", String.valueOf(idAlumno))
                .buscarPrimero();
        alumno.habilitar();
        actualizadorAlumno.actualizar(alumno);
    }

    public void deshabilitarAlumno(int idAlumno) throws RecursoNoEncontradoException {
        Alumno alumno = buscadorAlumno
                .igual("id", String.valueOf(idAlumno))
                .buscarPrimero();
        alumno.deshabilitar();
        actualizadorAlumno.actualizar(alumno);
    }

}