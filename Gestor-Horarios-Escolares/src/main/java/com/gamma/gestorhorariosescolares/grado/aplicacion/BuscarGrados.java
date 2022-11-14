package com.gamma.gestorhorariosescolares.grado.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;

import java.util.List;

public class BuscarGrados {

    private final ServicioBuscador<Grado> buscadorGrado;

    public BuscarGrados(ServicioBuscador<Grado> buscadorGrado) {
        this.buscadorGrado = buscadorGrado;
    }

    public GradosData buscarTodos() {
        List<Grado> grados = buscadorGrado
                .ordenarAscendente("clave")
                .buscar();

        return new GradosData(grados);
    }

    public GradosData buscarPorPlanEstudio(Integer idPlanEstudio) {
        List<Grado> grados = buscadorGrado
                .igual("idPlanEstudio", idPlanEstudio.toString())
                .ordenarAscendente("clave")
                .buscar();

        return new GradosData(grados);
    }

    public GradosData buscarHabilitadosPorPlanEstudio(Integer idPlanEstudio) {
        List<Grado> grados = buscadorGrado
                .igual("idPlanEstudio", idPlanEstudio.toString())
                .ordenarAscendente("clave")
                .buscar();

        return new GradosData(grados);
    }
}
