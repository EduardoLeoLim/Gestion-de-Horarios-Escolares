package com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.compartido.servicios.ServicioBuscador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorAdministrador implements ServicioBuscador<Administrador> {
    private final AdministradorRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorAdministrador(AdministradorRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }


    @Override
    public BuscadorAdministrador igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorAdministrador ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorAdministrador ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorAdministrador intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorAdministrador limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Administrador> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Administrador> listaAdministradores = repositorio.buscar(criterio);
        filtros.clear();
        return listaAdministradores;
    }
}
