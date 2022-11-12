package com.gamma.gestorhorariosescolares.inscripcion.dominio;

import java.time.Instant;
import java.util.Date;

public class Inscripcion {
    private final int id;
    private final Date fechaRegistro;
    private final int idGrado;
    private final int idPeriodoEscolar;
    private final int idAlumno;

    public Inscripcion(int id, Date fechaRegistro, int idGrado, int idPeriodoEscolar, int idAlumno) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.idGrado = idGrado;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idAlumno = idAlumno;
    }

    public Inscripcion(int idGrado, int idPeriodoEscolar, int idAlumno) {
        this.id = 0;
        this.fechaRegistro = Date.from(Instant.now());
        this.idGrado = idGrado;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idAlumno = idAlumno;
    }

    public int id() {
        return id;
    }

    public Date fechaRegistro() {
        return fechaRegistro;
    }

    public int idGrado() {
        return idGrado;
    }

    public int idPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public int idAlumno() {
        return idAlumno;
    }
}