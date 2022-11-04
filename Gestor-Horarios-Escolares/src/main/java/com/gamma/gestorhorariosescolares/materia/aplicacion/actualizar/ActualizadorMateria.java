package com.gamma.gestorhorariosescolares.materia.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.materia.dominio.MateriaRepositorio;

public class ActualizadorMateria implements ServicioActualizadorMateria {

    private final MateriaRepositorio repositorio;

    public ActualizadorMateria(MateriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Materia materia) {
        //Validar datos aquí

        repositorio.actualizar(materia);
    }

}