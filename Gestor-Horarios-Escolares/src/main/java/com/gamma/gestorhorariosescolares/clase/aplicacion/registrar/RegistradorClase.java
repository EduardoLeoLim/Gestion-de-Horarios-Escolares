package com.gamma.gestorhorariosescolares.clase.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.clase.dominio.ClaseRepositorio;

public class RegistradorClase implements ServicioRegistradorClase {

    private final ClaseRepositorio repositorio;

    public RegistradorClase(ClaseRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(int idGrupo, int idMateria) {

        Clase clase = new Clase(idGrupo, idMateria);
        return repositorio.registar(clase);
    }

}