package com.gamma.gestorhorariosescolares.evaluacion.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.EvaluacionRepositorio;

public class RegistradorEvaluacion implements ServicioRegistradorEvaluacion {

    private final EvaluacionRepositorio repositorio;

    public RegistradorEvaluacion(EvaluacionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public int registrar(String calificacion, String tipo, Integer idMateria, Integer idAlumno, Integer idInscripcion) {
        Evaluacion nuevaEvaluacion = new Evaluacion(calificacion, tipo, idMateria, idAlumno, idInscripcion);
        return repositorio.registar(nuevaEvaluacion);
    }

}