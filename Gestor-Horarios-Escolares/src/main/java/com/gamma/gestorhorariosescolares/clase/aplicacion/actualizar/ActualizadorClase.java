package com.gamma.gestorhorariosescolares.clase.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.clase.dominio.ClaseRepositorio;

public class ActualizadorClase implements ServicioActualizadorClase {

    private final ClaseRepositorio repositorio;

    public ActualizadorClase(ClaseRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Clase clase) {
        repositorio.actualizar(clase);
    }

}