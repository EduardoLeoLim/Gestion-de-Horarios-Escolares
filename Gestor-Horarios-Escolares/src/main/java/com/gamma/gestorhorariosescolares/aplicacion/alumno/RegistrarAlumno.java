package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.aplicacion.usuario.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.RegistradorUsuario;

public class RegistrarAlumno {
    private final ServicioRegistradorAlumno registradorAlumno;
    private final ServicioBuscadorAlumno buscadorAlumno;
    private final RegistradorUsuario registradorUsuario;
    private final BuscadorUsuario buscadorUsuario;

    public RegistrarAlumno(ServicioRegistradorAlumno registradorAlumno, ServicioBuscadorAlumno buscadorAlumno,
                           RegistradorUsuario registradorUsuario, BuscadorUsuario buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.registradorAlumno = registradorAlumno;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void Registrar(){

    }
}
