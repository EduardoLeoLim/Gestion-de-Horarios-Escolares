package com.gamma.gestorhorariosescolares.evaluacion.aplicacion.registrar;

public interface ServicioRegistradorEvaluacion {

    int registrar(String calificacion, String tipo, Integer idMateria, Integer idAlumno);
}
