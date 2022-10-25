package com.gamma.gestorhorariosescolares.maestro.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroRepositorio;

public class ActualizadorMaestro implements ServicioActualizadorMaestro {

    private final MaestroRepositorio repositorio;

    public ActualizadorMaestro(MaestroRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Maestro maestro) {
        //Validar formato de datos
        repositorio.actualizar(maestro);
    }
}
