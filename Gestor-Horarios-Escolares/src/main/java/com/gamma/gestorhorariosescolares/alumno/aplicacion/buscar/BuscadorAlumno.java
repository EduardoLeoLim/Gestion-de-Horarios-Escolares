package com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorAlumno implements ServicioBuscadorAlumno {
    private final AlumnoRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorAlumno(AlumnoRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public BuscadorAlumno igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorAlumno diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorAlumno mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorAlumno mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorAlumno menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorAlumno menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorAlumno contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorAlumno noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorAlumno ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorAlumno ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorAlumno intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorAlumno limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Alumno> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Alumno> listaAlumnos = repositorio.buscar(criterio);
        filtros.clear();
        return listaAlumnos;
    }
}
