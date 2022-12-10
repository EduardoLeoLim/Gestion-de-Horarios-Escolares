package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;

public class ClaseAlumnoData {
    private final Integer idClase;
    private final Integer idGrupo;
    private final ClaseMateriaData claseMateriaData;
    private final ClaseMaestroData claseMaestroData;

    public ClaseAlumnoData(Integer idClase,Integer idGrupo, ClaseMateriaData claseMateriaData, ClaseMaestroData claseMaestroData){
        this.idClase = idClase;
        this.idGrupo = idGrupo;
        this.claseMateriaData = claseMateriaData;
        this.claseMaestroData = claseMaestroData;

    }

    public static ClaseAlumnoData fromAggregate(Clase clase, Grupo grupo, ClaseMateriaData claseMateriaData, ClaseMaestroData claseMaestroData){
        return new ClaseAlumnoData(clase.id().value(), grupo.id(), claseMateriaData, claseMaestroData);

    }

    public Integer idClase(){
        return idClase;
    }

    public Integer idGrupo(){
        return idGrupo;
    }

    public ClaseMateriaData claseMateriaData(){
        return claseMateriaData;
    }

    public ClaseMaestroData claseMaestroData(){
        return claseMaestroData;
    }











}
