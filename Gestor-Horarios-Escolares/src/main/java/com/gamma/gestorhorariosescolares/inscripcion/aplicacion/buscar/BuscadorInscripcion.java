package com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.InscripcionRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorInscripcion implements ServicioBuscador<Inscripcion> {

    private final InscripcionRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorInscripcion(InscripcionRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public BuscadorInscripcion igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorInscripcion ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorInscripcion ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorInscripcion esObligatorio() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).obligatory();
        return this;
    }

    @Override
    public BuscadorInscripcion esOpcional() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).optional();
        return this;
    }

    @Override
    public BuscadorInscripcion intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorInscripcion limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Inscripcion> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Inscripcion> listaInscripciones = repositorio.buscar(criterio);

        limpiarFiltros();

        return listaInscripciones;
    }

    @Override
    public Inscripcion buscarPrimero() throws RecursoNoEncontradoException {
        List<Inscripcion> listaInscripciones = this.buscar();

        if (listaInscripciones.isEmpty())
            throw new RecursoNoEncontradoException("No se encontró la inscripción.");
        return listaInscripciones.get(0);
    }

    private void limpiarFiltros() {
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }
}
