package com.gamma.gestorhorariosescolares.materia.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.materia.dominio.MateriaRepositorio;

public class RegistradorMateria implements ServicioRegistradorMateria {

    private final MateriaRepositorio repositorio;

    public RegistradorMateria(MateriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String clave, String nombre, int horasPracticas, int horasTeoricas) {
        Materia materia = new Materia(clave, nombre, horasPracticas, horasTeoricas);
        //Validar datos aquí
        return repositorio.registrar(materia);
    }

}