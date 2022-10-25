package com.gamma.gestorhorariosescolares.maestro.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroRepositorio;

public class RegistradorMaestro implements ServicioRegistradorMaestro {

    private final MaestroRepositorio repositorio;

    public RegistradorMaestro(MaestroRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        //Validar formato de datos
        Maestro maestro = new Maestro(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
        repositorio.registrar(maestro);
    }
}
