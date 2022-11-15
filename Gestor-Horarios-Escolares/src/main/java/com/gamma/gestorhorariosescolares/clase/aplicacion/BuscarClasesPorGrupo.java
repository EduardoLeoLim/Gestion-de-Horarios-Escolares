package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class BuscarClasesPorGrupo {

    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Maestro> buscadorMaestro;

    public BuscarClasesPorGrupo(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Materia> buscadorMateria,
                                ServicioBuscador<Maestro> buscadorMaestro) {
        this.buscadorClase = buscadorClase;
        this.buscadorMateria = buscadorMateria;
        this.buscadorMaestro = buscadorMaestro;
    }

//    public ClasesData buscar(GrupoData grupo) {
//        List<Clase> clases = buscadorClase
//                .igual("idGrupo", grupo.id().toString())
//                .buscar();
//
//        return clases.stream().map(clase -> {
//            Materia materia;
//            Maestro maestro;
//
//            return ClaseData.fromAggregate(clase, grupo, MateriaData.fromAggregate(materia, null, null));
//        }).collect(Collectors.toList());
//    }
}
