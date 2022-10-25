package com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorMaestro implements ServicioBuscador<Maestro> {

    private final MaestroRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorMaestro(MaestroRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public BuscadorMaestro igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorMaestro diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorMaestro mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorMaestro mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorMaestro menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorMaestro menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorMaestro contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorMaestro noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorMaestro ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorMaestro ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorMaestro intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorMaestro limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Maestro> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Maestro> listaMaestros = repositorio.buscar(criterio);
        //Resetaer para reutilizar instacia
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
        return listaMaestros;
    }
}
