package com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.GrupoRepositorio;

public class ActualizadorGrupo implements ServicioActualizadorGrupo {

    private final GrupoRepositorio repositorio;

    public ActualizadorGrupo(GrupoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Grupo grupo) {
        repositorio.actualizar(grupo);
    }

}