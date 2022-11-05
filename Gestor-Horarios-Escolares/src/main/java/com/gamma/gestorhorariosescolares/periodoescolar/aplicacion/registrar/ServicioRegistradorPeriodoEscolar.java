package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.registrar;

import java.util.Date;

public interface ServicioRegistradorPeriodoEscolar {
    int registrar(String clave, String nombre, Date fechaInicio, Date fechaFin);
}
