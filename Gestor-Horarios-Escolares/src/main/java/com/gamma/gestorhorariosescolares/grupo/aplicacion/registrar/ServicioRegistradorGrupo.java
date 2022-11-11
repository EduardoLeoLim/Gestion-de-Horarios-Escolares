package com.gamma.gestorhorariosescolares.grupo.aplicacion.registrar;

public interface ServicioRegistradorGrupo {
    int registrar(String clave, String nombre, int idGrado, int[] idMaterias, int[] idInscripciones, int idPeriodoEscolar);
}
