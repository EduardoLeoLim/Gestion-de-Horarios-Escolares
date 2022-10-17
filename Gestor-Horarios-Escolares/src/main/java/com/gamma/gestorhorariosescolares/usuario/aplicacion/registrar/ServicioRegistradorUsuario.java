package com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar;

public interface ServicioRegistradorUsuario {
    int registrar(String correoElectronico, String claveAcceso, String tipo);
}
