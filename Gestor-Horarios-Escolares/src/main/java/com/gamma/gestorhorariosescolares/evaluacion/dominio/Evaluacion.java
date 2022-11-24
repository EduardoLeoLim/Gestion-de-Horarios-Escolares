package com.gamma.gestorhorariosescolares.evaluacion.dominio;

public class Evaluacion {
    private int id;
    private String calificacion;
    private String tipo;
    private Integer idMateria;
    private Integer idAlumno;


    public Evaluacion(int id, String calificacion, String tipo, Integer idMateria, Integer idAlumno) {
        this.id = id;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.idMateria = idMateria;
        this.idAlumno = idAlumno;
    }

    public Evaluacion(String calificacion, String tipo, Integer idMateria, Integer idAlumno){
        this.id = 0;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.idMateria = idMateria;
        this.idAlumno = idAlumno;
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



}
