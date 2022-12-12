package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMaestroData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaData;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;

import java.util.ArrayList;
import java.util.List;

public class BuscarEvaluacionesDeAlumno {


    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioBuscador<Evaluacion> buscadorEvaluacion;

    public BuscarEvaluacionesDeAlumno(ServicioBuscador<Inscripcion> buscadorInscripcion,ServicioBuscador<Materia> buscadorMateria,ServicioBuscador<Maestro> buscadorMaestro,
                                      ServicioBuscador<Evaluacion> buscadorEvaluacion){

        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorMateria = buscadorMateria;
        this.buscadorMaestro = buscadorMaestro;
        this.buscadorEvaluacion = buscadorEvaluacion;

    }

    public EvaluacionesData buscarEvaluaciones(Integer periodoEscolar, Integer idAlumno) throws RecursoNoEncontradoException {
        Inscripcion inscripcion;
        inscripcion = buscadorInscripcion.igual("idPeriodoEscolar",periodoEscolar.toString()).igual("idAlumno", idAlumno.toString()).buscarPrimero();
        List<Evaluacion> evaluaciones = buscadorEvaluacion.igual("idInscripcion", String.valueOf(inscripcion.id())).buscar();
        List<EvaluacionData> evaluacionesData = new ArrayList<>();
        for (Evaluacion evaluacion:evaluaciones){
            Materia materia = buscadorMateria.igual("id", evaluacion.idMateria().toString()).buscarPrimero();
            Maestro maestro = buscadorMaestro.igual("id", evaluacion.idMaestro().toString()).buscarPrimero();
            ClaseMaestroData maestroData = ClaseMaestroData.fromAggregate(maestro);
            ClaseMateriaData materiaData = ClaseMateriaData.fromAggregate(materia);
            EvaluacionData evaluacionData = EvaluacionData.fromAgggregate(evaluacion, maestroData, materiaData);
            evaluacionesData.add(evaluacionData);

        }

        return new EvaluacionesData(evaluacionesData);


    }


}
