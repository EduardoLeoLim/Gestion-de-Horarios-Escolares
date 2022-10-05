package com.gamma.gestorhorariosescolares.aplicacion.alumno;

import com.gamma.gestorhorariosescolares.aplicacion.usuario.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.RegistradorUsuario;

public class RegistrarAlumno {
    private final RegistradorAlumno registradorAlumno;
    private final BuscadorAlumno buscadorAlumno;
    private final RegistradorUsuario registradorUsuario;
    private final BuscadorUsuario buscadorUsuario;

    public RegistrarAlumno(RegistradorAlumno registradorAlumno, BuscadorAlumno buscadorAlumno,
                           RegistradorUsuario registradorUsuario, BuscadorUsuario buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.registradorAlumno = registradorAlumno;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void Registrar(){

    }
}
