package com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;

import java.util.List;

public class BuscadorAlumno implements ServicioBuscadorAlumno {
    private final AlumnoRepositorio repositorio;
    private Criterio criterio;

    public BuscadorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
        criterio = new Criterio();
    }

    @Override
    public BuscadorAlumno filtarNombre(String nombre) {
        //Add filter to criterio
        return this;
    }

    @Override
    public BuscadorAlumno filtarApellidoPaterno(String apellidoPaterno) {
        //Add filter to criterio
        return this;
    }

    @Override
    public BuscadorAlumno filtarApellidoMaterno(String apellidoMaterno) {
        //Add filter to criterio
        return this;
    }

    @Override
    public BuscadorAlumno filtarCURP(String curp) {
        //Add filter to criterio
        return this;
    }

    @Override
    public BuscadorAlumno filtarMatricula(String matricula) {
        //Add filter to criterio
        return this;
    }

    @Override
    public List<Alumno> buscar() {
        List<Alumno> resultado = repositorio.buscar(criterio);
        criterio = new Criterio();//limpiar filtros si se reutiliza la instancia o definir método para limpiar filtros
        return resultado;
    }
}