package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.clase.aplicacion.registrar.ServicioRegistradorClase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.registrar.ServicioRegistradorGrupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.util.List;

public class RegistrarGrupo {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;
    private final ServicioRegistradorGrupo registradorGrupo;
    private final ServicioRegistradorClase registradorClase;


    public RegistrarGrupo(ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Grado> buscadorGrado,
                          ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar,
                          ServicioRegistradorGrupo registradorGrupo, ServicioRegistradorClase registradorClase) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorGrado = buscadorGrado;
        this.buscadorMateria = buscadorMateria;
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
        this.registradorGrupo = registradorGrupo;
        this.registradorClase = registradorClase;
    }

    public void registrar(String clave, String nombre, GradoData grado, PeriodoEscolarData periodoEscolar)
            throws RecursoNoEncontradoException, ClaveDuplicadaException {
        List<Grado> grados = buscadorGrado
                .igual("id", grado.id().toString())
                .igual("estatus", "1")
                .buscar();
        if (grados.isEmpty())
            throw new RecursoNoEncontradoException("El grado seleccionado no existe o no se encuentra disponible");

        List<PeriodoEscolar> periodosEscolares = buscadorPeriodoEscolar
                .igual("id", periodoEscolar.id().toString())
                .igual("estatus", "1")
                .buscar();
        if (periodosEscolares.isEmpty())
            throw new RecursoNoEncontradoException("El periodo escolar no existe o no se encuentra disponible");

        List<Grupo> grupos = buscadorGrupo
                .igual("clave", clave)
                .buscar();
        if (!grupos.isEmpty())
            throw new ClaveDuplicadaException("Ya existe un grupo registrado con la clave " + clave);

        //Registrar grupo
        int idGrupo = registradorGrupo.registrar(clave, nombre, grado.id(), periodoEscolar.id());

        List<Materia> materias = buscadorMateria
                .igual("idGrado", grado.id().toString())
                .igual("estatus", "1")
                .buscar();

        //Registrar clases
        materias.forEach(materia -> registradorClase.registrar(idGrupo, materia.id()));
    }
}
