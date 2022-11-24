package com.gamma.gestorhorariosescolares.evaluacion.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.MatriculaDuplicadaException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar.ServicioRegistradorAlumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.registrar.ServicioRegistradorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class RegistrarEvaluacion {

    private final ServicioRegistradorEvaluacion registradorEvaluacion;
    private final ServicioBuscador<Evaluacion> buscadorEvaluacion;

    public RegistrarEvaluacion(ServicioRegistradorEvaluacion registradorEvaluacion, ServicioBuscador<Evaluacion> buscadorEvaluacion ){
        this.registradorEvaluacion = registradorEvaluacion;
        this.buscadorEvaluacion = buscadorEvaluacion;

    }

    public void registrar(String calificacion, String tipo, Integer idMateria, Integer idAlumno) {

        registradorEvaluacion.registrar(calificacion,tipo,idMateria,idAlumno);




    }

}
