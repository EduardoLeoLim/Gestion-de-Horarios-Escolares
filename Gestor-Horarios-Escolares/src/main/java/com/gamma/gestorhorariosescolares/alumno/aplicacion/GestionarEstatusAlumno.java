package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar.ActualizadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;

import java.util.List;

public class GestionarEstatusAlumno {

    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ActualizadorAlumno actualizadorAlumno;

    public GestionarEstatusAlumno(ServicioBuscador<Alumno> buscadorAlumno, ActualizadorAlumno actualizadorAlumno) {
        this.buscadorAlumno = buscadorAlumno;
        this.actualizadorAlumno = actualizadorAlumno;
    }

    public void habilitarAlumno(int idAlumno) throws RecursoNoEncontradoException {
        Alumno alumno = this.buscarAlumno(idAlumno);
        alumno.habilitar();
        actualizadorAlumno.actualizar(alumno);
    }

    public void deshabilitarAlumno(int idAlumno) throws RecursoNoEncontradoException {
        Alumno alumno = this.buscarAlumno(idAlumno);
        alumno.habilitar();
        actualizadorAlumno.actualizar(alumno);
    }

    private Alumno buscarAlumno(int idAlumno) throws RecursoNoEncontradoException {
        List<Alumno> alumnos = buscadorAlumno.igual("id", String.valueOf(idAlumno)).buscar();
        if (alumnos.size() == 0)
            throw new RecursoNoEncontradoException("El usuario no se encuentra registrado en el sistema");

        return alumnos.get(0);
    }
}
