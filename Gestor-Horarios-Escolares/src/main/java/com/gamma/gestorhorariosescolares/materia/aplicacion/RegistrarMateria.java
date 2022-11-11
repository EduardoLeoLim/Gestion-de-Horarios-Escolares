package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.materia.aplicacion.registrar.RegistradorMateria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;

public class RegistrarMateria {

    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final RegistradorMateria registradorMateria;

    public RegistrarMateria(ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<Grado> buscadorGrado,
                            RegistradorMateria registradorMateria) {
        this.buscadorMateria = buscadorMateria;
        this.buscadorGrado = buscadorGrado;
        this.registradorMateria = registradorMateria;
    }

    public void registrar(String clave, String nombre, int horasPracticas, int horasTeoricas, GradoData grado)
            throws ClaveDuplicadaException, RecursoNoEncontradoException {
        //Validaciones
        List<Grado> grados = buscadorGrado
                .igual("id", grado.id().toString())
                .buscar();
        if (grados.isEmpty())
            throw new RecursoNoEncontradoException("El grado seleccionado no se encuentra registrado en el sistema.");

        List<Materia> materias = buscadorMateria
                .igual("clave", clave)
                .buscar();
        if (!materias.isEmpty())
            throw new ClaveDuplicadaException("Ya hay una materia registrada con la clave " + clave);

        //Registro
        registradorMateria.registrar(clave, nombre, horasPracticas, horasTeoricas, grado.id());
    }

}