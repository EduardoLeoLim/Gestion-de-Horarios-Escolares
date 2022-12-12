package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.ArrayList;
import java.util.List;

public class BuscarClasesDeMaestro {

    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Materia> buscadorMateria;

    public BuscarClasesDeMaestro(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Materia> buscadorMateria) {
        this.buscadorClase = buscadorClase;
        this.buscadorMateria = buscadorMateria;
    }

    public ClasesMateriaMaestroData buscar(Integer idPeriodoEscolar, Integer idMaestro) throws RecursoNoEncontradoException {
        List<Clase> clases = buscadorClase.igual("idPeriodoEscolar", idPeriodoEscolar.toString()).igual("idMaestro", idMaestro.toString()).buscar();
        List<ClaseMateriaMaestroData> materiasMaestro = new ArrayList<>();
        for (Clase clase:clases){
            Materia materia = buscadorMateria.igual("id", String.valueOf(clase.idMateria())).buscarPrimero();
            ClaseMateriaData claseMateriaData = ClaseMateriaData.fromAggregate(materia);
            ClaseMateriaMaestroData claseMateriaMaestroData = ClaseMateriaMaestroData.fromAggregate(clase, claseMateriaData);
            materiasMaestro.add(claseMateriaMaestroData);
        }

        return new ClasesMateriaMaestroData(materiasMaestro);

    }
}

