package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoInscripcionData;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.ArrayList;
import java.util.List;

public class BuscarEvaluacion {
    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ServicioBuscador<Evaluacion> buscadorEvaluacion;

    public BuscarEvaluacion(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Grupo> buscadorGrupo
            , ServicioBuscador<Inscripcion> buscadorInscripcion
            , ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<Alumno> buscadorAlumno
            , ServicioBuscador<Evaluacion> buscadorEvaluacion) {
        this.buscadorClase = buscadorClase;
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorMateria = buscadorMateria;
        this.buscadorAlumno = buscadorAlumno;
        this.buscadorEvaluacion = buscadorEvaluacion;
    }

    public EvaluacionesInscripcionData buscarEvaluaciones(Integer idClase) throws RecursoNoEncontradoException {
        Clase clase = buscadorClase.igual("id", idClase.toString()).buscarPrimero();
        Grupo grupo = buscadorGrupo.igual("id", String.valueOf(clase.idGrupo())).buscarPrimero();

        for (int idInscripcion : grupo.idInscripciones()) {
            buscadorInscripcion.igual("id", String.valueOf(idInscripcion));
            if (grupo.idInscripciones().length > 1)
                buscadorInscripcion.esOpcional();
        }
        List<Inscripcion> inscripciones = buscadorInscripcion.buscar();
        List<EvaluacionInscripcionData> evaluacionesInscripcionData = new ArrayList<>();

        for (Inscripcion inscripcion:inscripciones){
            Alumno alumno = buscadorAlumno.igual("id", String.valueOf(inscripcion.idAlumno())).buscarPrimero();
            Materia materia = buscadorMateria.igual("id", String.valueOf(clase.idMateria())).buscarPrimero();
            Integer idMaestro = 0;
            if (clase.idMaestro() != null)
                idMaestro = clase.idMaestro().value();
            Evaluacion evaluacion;


            try{
                evaluacion = buscadorEvaluacion.igual("idInscripcion", String.valueOf(inscripcion.id())).buscarPrimero();
            }catch (RecursoNoEncontradoException ex){
                evaluacion = new Evaluacion(0, "Sin calificar", "Ordinario", materia.id(), alumno.id(), grupo.id(), idMaestro, inscripcion.id() );
            }



            evaluacionesInscripcionData.add(EvaluacionInscripcionData.fromAggregate(evaluacion, AlumnoInscripcionData.fromAggregate(alumno)
                    , ClaseMateriaData.fromAggregate(materia)));

        }

        return new EvaluacionesInscripcionData(evaluacionesInscripcionData);




    }
}
