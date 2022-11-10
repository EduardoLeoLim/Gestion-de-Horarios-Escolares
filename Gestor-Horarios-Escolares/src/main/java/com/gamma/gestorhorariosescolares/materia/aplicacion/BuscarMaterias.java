package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarMaterias {

    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final ServicioBuscador<PlanEstudio> buscadorPlanEstudio;

    public BuscarMaterias(ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<Grado> buscadorGrado,
                          ServicioBuscador<PlanEstudio> buscadorPlanEstudio) {
        this.buscadorMateria = buscadorMateria;
        this.buscadorGrado = buscadorGrado;
        this.buscadorPlanEstudio = buscadorPlanEstudio;
    }

    public MateriasData buscarTodos() {
        List<Materia> materias = buscadorMateria
                .ordenarAscendente("clave")
                .buscar();

        return new MateriasData(prepararMateriasData(materias));
    }

    public MateriasData buscarHabilitados() {
        List<Materia> materias = buscadorMateria
                .igual("estatus", "1")
                .ordenarAscendente("clave")
                .buscar();
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio.buscar();
        List<Grado> grados = buscadorGrado.buscar();

        return new MateriasData(prepararMateriasData(materias));
    }

    public MateriasData buscarPorCriterio(String criterio) {
        List<Materia> materias = buscadorMateria
                .contiene("clave", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("horasPracticas", criterio).esOpcional()
                .contiene("horasTeoricas", criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        return new MateriasData(prepararMateriasData(materias));
    }

    public MateriasData buscarPorGrado(GradoData grado){
        List<Materia> materias = buscadorMateria
                .igual("idGrado", grado.id().toString())
                .ordenarAscendente("clave")
                .buscar();
        return new MateriasData(prepararMateriasData(materias));
    }

    /**
     * A partir de una lista de Materia genera una lista de MateriaData
     *
     * @param materias lista de Materia
     * @return Lista de MateriaData
     */
    private List<MateriaData> prepararMateriasData(List<Materia> materias) {
        List<PlanEstudio> planesEstudio = buscadorPlanEstudio.buscar();
        List<Grado> grados = buscadorGrado.buscar();

        return materias.stream().map(materia -> {
            Grado gradoMateria = grados.stream()
                    .filter(grado -> grado.id() == materia.idGrado())
                    .findFirst()
                    .orElse(new Grado(0, "", "", 0, false));
            PlanEstudio planEstudioMateria = planesEstudio.stream()
                    .filter(planEstudio -> planEstudio.id() == gradoMateria.idPlanEstudio())
                    .findFirst()
                    .orElse(new PlanEstudio(0,"","", false));

            return MateriaData.fromAggregate(materia, planEstudioMateria, gradoMateria);
        }).collect(Collectors.toList());
    }
}
