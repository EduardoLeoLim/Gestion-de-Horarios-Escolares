package com.gamma.gestorhorariosescolares.grupo.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.GrupoRepositorio;

public class RegistradorGrupo implements ServicioRegistradorGrupo {

    private final GrupoRepositorio repositorio;

    public RegistradorGrupo(GrupoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, String nombre, int idGrado, int[] idMaterias, int[] idInscripciones, int idPeriodoEscolar) {
        Grupo grupo = new Grupo(clave, nombre, idGrado, idMaterias, idInscripciones, idPeriodoEscolar);
        return repositorio.registrar(grupo);
    }
}
