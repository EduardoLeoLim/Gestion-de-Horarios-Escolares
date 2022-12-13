package com.gamma.gestorhorariosescolares.evaluacion.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.EvaluacionRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorEvaluacion implements ServicioBuscador<Evaluacion> {

    private final EvaluacionRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorEvaluacion(EvaluacionRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public BuscadorEvaluacion igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorEvaluacion ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorEvaluacion ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorEvaluacion esObligatorio() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).obligatory();
        return this;
    }

    @Override
    public BuscadorEvaluacion esOpcional() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).optional();
        return this;
    }

    @Override
    public BuscadorEvaluacion intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorEvaluacion limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Evaluacion> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, limite, intervalo);
        List<Evaluacion> listaEvaluaciones = repositorio.buscar(criterio);

        limpiarFiltros();

        return listaEvaluaciones;
    }

    @Override
    public Evaluacion buscarPrimero() throws RecursoNoEncontradoException {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, limite, intervalo);
        List<Evaluacion> listaEvaluaciones = repositorio.buscar(criterio);

        limpiarFiltros();

        if (listaEvaluaciones.isEmpty())
            throw new RecursoNoEncontradoException("No se encontró ningúna evaluacion.");
        return listaEvaluaciones.get(0);
    }

    private void limpiarFiltros() {
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

}