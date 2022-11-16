package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;

public class AlumnoInscripcionData {

    private final int id;
    private final String matricula;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;

    public AlumnoInscripcionData(int id, String matricula, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public static AlumnoInscripcionData fromAggregate(Alumno alumno) {
        return new AlumnoInscripcionData(alumno.id(), alumno.matricula(), alumno.nombre(), alumno.apellidoPaterno(),
                alumno.apellidoMaterno());
    }

    public int id() {
        return id;
    }

    public String matricula() {
        return matricula;
    }

    public String nombre() {
        return nombre;
    }

    public String apellidoPaterno() {
        return apellidoPaterno;
    }

    public String apellidoMaterno() {
        return apellidoMaterno;
    }
}
