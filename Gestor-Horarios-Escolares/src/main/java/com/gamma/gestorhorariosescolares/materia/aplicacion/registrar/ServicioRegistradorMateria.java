package com.gamma.gestorhorariosescolares.materia.aplicacion.registrar;

public interface ServicioRegistradorMateria {
    int registrar(String clave, String nombre, int horasPracticas, int horasTeoricas, int idGrado);
}
