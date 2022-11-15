package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroClaseData;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaClaseData;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;
import java.util.stream.Collectors;

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

    public ClasesGrupoData buscar(Integer idGrupo) {
        List<Clase> clases = buscadorClase
                .igual("idGrupo", idGrupo.toString())
                .buscar();

        List<ClaseGrupoData> clasesGrupoData = clases.stream().map(clase -> {
            Materia materia;
            Maestro maestro;
            MateriaClaseData materiaClaseData;
            MaestroClaseData maestroClaseData = null;

            try {
                materia = buscadorMateria
                        .igual("id", String.valueOf(clase.idMateria()))
                        .buscarPrimero();
                materiaClaseData = MateriaClaseData.fromAggregate(materia);
            } catch (RecursoNoEncontradoException e) {
                materiaClaseData = null;
            }

            if (clase.idMaestro() == null) {
                try {
                    maestro = buscadorMaestro
                            .igual("id", clase.idMaestro().value().toString())
                            .buscarPrimero();
                    maestroClaseData = MaestroClaseData.fromAggregate(maestro);
                } catch (RecursoNoEncontradoException ignored) {
                }
            }

            return ClaseGrupoData.fromAggregate(clase, materiaClaseData, maestroClaseData);
        }).collect(Collectors.toList());

        return new ClasesGrupoData(clasesGrupoData);
    }
}
