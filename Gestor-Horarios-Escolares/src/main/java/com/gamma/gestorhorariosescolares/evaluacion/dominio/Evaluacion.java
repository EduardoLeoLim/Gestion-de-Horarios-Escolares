package com.gamma.gestorhorariosescolares.evaluacion.dominio;

public class Evaluacion {
    private int id;
    private String calificacion;
    private String tipo;
    private Integer idMateria;
    private Integer idAlumno;

    private Integer idGrupo;

    private Integer idMaestro;

    private Integer idInscripcion;







    public Evaluacion(int id, String calificacion, String tipo, Integer idMateria, Integer idAlumno, Integer idGrupo, Integer idMaestro, Integer idInscripcion) {
        this.id = id;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.idMateria = idMateria;
        this.idAlumno = idAlumno;
        this.idGrupo = idGrupo;
        this.idMaestro = idMaestro;
        this.idInscripcion = idInscripcion;
    }

    public Evaluacion(String calificacion, String tipo, Integer idMateria, Integer idAlumno, Integer idInscripcion ){
        this.id = 0;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.idMateria = idMateria;
        this.idAlumno = idAlumno;
        this.idInscripcion = idInscripcion;

    }

    public int id(){
        return id;
    }

    public String calificacion(){
        return calificacion;
    }

    public String tipo(){
        return tipo;
    }

    public Integer idMateria(){
        return idMateria;
    }

    public Integer idAlumno(){
        return idAlumno;

    }

    public Integer idGrupo() {
        return idGrupo;
    }

    public Integer idMaestro() {
        return idMaestro;
    }

    public Integer idInscripcion() {
        return idInscripcion;
    }
}
