package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.materia.aplicacion.actualizar.ActualizadorMateria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;

public class ActualizarMateria {

    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final ActualizadorMateria actualizadorMateria;

    public ActualizarMateria(ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<Grado> buscadorGrado,
                             ActualizadorMateria actualizadorrMateria) {
        this.buscadorMateria = buscadorMateria;
        this.buscadorGrado = buscadorGrado;
        this.actualizadorMateria = actualizadorrMateria;
    }

    public void actualizar(MateriaData materiaData) throws RecursoNoEncontradoException, ClaveDuplicadaException {
        //Validaciones
        if (materiaData == null)
            throw new NullPointerException();

        List<Grado> grados = buscadorGrado
                .igual("id", materiaData.grado().id().toString())
                .buscar();
        if (grados.isEmpty())
            throw new RecursoNoEncontradoException("El grado seleccionado no se encuentra registrado en el sistema.");

        List<Materia> materias;

        materias = buscadorMateria
                .igual("id", materiaData.id().toString())
                .buscar();
        if (materias.isEmpty())
            throw new RecursoNoEncontradoException("La materia que se desea actualizar no se encuentra registrada en el sistema");

        materias = buscadorMateria
                .diferente("id", materiaData.id().toString())
                .igual("clave", materiaData.clave())
                .buscar();
        if (!materias.isEmpty())
            throw new ClaveDuplicadaException("Ya hay una materia registrada con la clave " + materiaData.clave());

        //Actualización
        Materia materia = new Materia(materiaData.id(), materiaData.clave(), materiaData.nombre(),
                materiaData.horasPracticas(), materiaData.horasTeoricas(), materiaData.grado().id(),
                materiaData.estatus());

        actualizadorMateria.actualizar(materia);
    }
}