package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.registrar.ServicioRegistradorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;

public class RegistrarEvaluacion {

    private final ServicioRegistradorEvaluacion registradorEvaluacion;
    private final ServicioBuscador<Evaluacion> buscadorEvaluacion;

    public RegistrarEvaluacion(ServicioRegistradorEvaluacion registradorEvaluacion, ServicioBuscador<Evaluacion> buscadorEvaluacion) {
        this.registradorEvaluacion = registradorEvaluacion;
        this.buscadorEvaluacion = buscadorEvaluacion;
    }

    public void registrar(String calificacion, String tipo, Integer idMateria, Integer idAlumno, Integer idInscripcion) {
        registradorEvaluacion.registrar(calificacion, tipo, idMateria, idAlumno, idInscripcion);
    }

}