package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;

public class BuscarMaterias {

    private final ServicioBuscador<Materia> buscadorMateria;

    public BuscarMaterias(ServicioBuscador<Materia> buscadorMateria) {
        this.buscadorMateria = buscadorMateria;
    }

    public MateriasData buscarTodos() {
        List<Materia> materias = buscadorMateria
                .ordenarAscendente("clave")
                .buscar();

        return new MateriasData(materias);
    }

    public MateriasData buscarHabilitados() {
        List<Materia> materias = buscadorMateria
                .igual("estatus", "1")
                .ordenarAscendente("clave")
                .buscar();

        return new MateriasData(materias);
    }

    public MateriasData buscarPorCriterio(String criterio) {
        List<Materia> materias = buscadorMateria
                .contiene("clave", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("horasPracticas", criterio).esOpcional()
                .contiene("horasTeoricas", criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        return new MateriasData(materias);
    }
}
