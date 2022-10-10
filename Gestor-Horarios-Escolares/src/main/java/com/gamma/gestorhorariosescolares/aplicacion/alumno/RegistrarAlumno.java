package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.aplicacion.usuario.ServicioBuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.ServicioRegistradorUsuario;

public class RegistrarAlumno {
    private final ServicioRegistradorAlumno registradorAlumno;
    private final ServicioBuscadorAlumno buscadorAlumno;
    private final ServicioRegistradorUsuario registradorUsuario;
    private final ServicioBuscadorUsuario buscadorUsuario;

    public RegistrarAlumno(ServicioRegistradorAlumno registradorAlumno, ServicioBuscadorAlumno buscadorAlumno,
                           ServicioRegistradorUsuario registradorUsuario, ServicioBuscadorUsuario buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.registradorAlumno = registradorAlumno;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void Registrar(){

    }
}
