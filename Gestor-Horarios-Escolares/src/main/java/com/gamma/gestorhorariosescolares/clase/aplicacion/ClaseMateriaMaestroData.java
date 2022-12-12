package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;

public class ClaseMateriaMaestroData {

    private final Integer idClase;

    private final Integer idGrupo;
    private final ClaseMateriaData materia;



    public ClaseMateriaMaestroData(Integer idClase,Integer idGrupo, ClaseMateriaData materia) {
        this.idClase = idClase;
        this.idGrupo = idGrupo;
        this.materia = materia;

    }

    public static ClaseMateriaMaestroData fromAggregate(Clase clase, ClaseMateriaData materia){
        return new ClaseMateriaMaestroData(clase.id().value(),clase.idGrupo(), materia);

    }

    public Integer idClase() {
        return idClase;
    }

    public Integer idGrupo() {
        return idGrupo;
    }

    public ClaseMateriaData materia() {
        return materia;
    }


}
