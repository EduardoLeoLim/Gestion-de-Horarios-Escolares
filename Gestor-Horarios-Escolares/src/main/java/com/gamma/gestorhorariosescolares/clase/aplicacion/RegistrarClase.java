package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.registrar.ServicioRegistradorClase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

public class RegistrarClase {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioRegistradorClase registradorClase;

    public RegistrarClase(ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Materia> buscadorMateria,
                          ServicioRegistradorClase registradorClase) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorMateria = buscadorMateria;
        this.registradorClase = registradorClase;
    }

    public void registrar(int idGrupo, int idMateria) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo
                .igual("id", String.valueOf(idGrupo))
                .buscarPrimero();
        Materia materia = buscadorMateria
                .igual("id", String.valueOf(idMateria))
                .igual("estatus", "1")
                .buscarPrimero();

        if (materia.idGrado() != grupo.idGrado())
            return;//Crear y lanzar excepción

        registradorClase.registrar(idGrupo, idMateria);
    }
}
