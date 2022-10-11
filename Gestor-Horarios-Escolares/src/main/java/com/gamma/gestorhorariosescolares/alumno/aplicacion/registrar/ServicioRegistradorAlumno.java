package com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar;

public interface ServicioRegistradorAlumno {
    int registrar(String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario);
}
