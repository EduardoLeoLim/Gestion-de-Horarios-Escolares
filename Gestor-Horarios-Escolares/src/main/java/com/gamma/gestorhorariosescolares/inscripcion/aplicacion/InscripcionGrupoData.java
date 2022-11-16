package com.gamma.gestorhorariosescolares.inscripcion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoInscripcionData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoInscripcionData;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;

import java.time.LocalDate;
import java.time.ZoneId;

public class InscripcionGrupoData {

    private final Integer id;
    private final LocalDate fechaRegistro;
    private final AlumnoInscripcionData alumno;
    private final GrupoInscripcionData grupo;

    public InscripcionGrupoData(Integer id, LocalDate fechaRegistro, AlumnoInscripcionData alumno, GrupoInscripcionData grupo) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.alumno = alumno;
        this.grupo = grupo;
    }

    public static InscripcionGrupoData fromAggregate(Inscripcion inscripcion, AlumnoInscripcionData alumno, GrupoInscripcionData grupo) {
        LocalDate fechaInscripcion = inscripcion.fechaRegistro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new InscripcionGrupoData(inscripcion.id(), fechaInscripcion, alumno, grupo);
    }

    public Integer id() {
        return id;
    }

    public LocalDate fechaRegistro() {
        return fechaRegistro;
    }

    public AlumnoInscripcionData alumno() {
        return alumno;
    }

    public GrupoInscripcionData grupo() {
        return grupo;
    }
}
