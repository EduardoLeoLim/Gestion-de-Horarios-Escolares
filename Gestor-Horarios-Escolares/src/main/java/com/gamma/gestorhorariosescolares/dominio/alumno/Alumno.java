package com.gamma.gestorhorariosescolares.dominio.alumno;

public class Alumno {
    private final int id;
    private final String matricula;
    private final String curp;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final int idUsuario;

    public Alumno(int id, String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.matricula = matricula;
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    public Alumno(String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        id = 0;
        this.matricula = matricula;
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }
}
