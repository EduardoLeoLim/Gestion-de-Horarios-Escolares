package com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.compartido.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorUsuario implements ServicioBuscador<Usuario> {

    public final UsuarioRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public BuscadorUsuario igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorUsuario diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorUsuario mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorUsuario mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorUsuario menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorUsuario menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorUsuario contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorUsuario noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorUsuario ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public BuscadorUsuario ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    @Override
    public BuscadorUsuario intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorUsuario limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Usuario> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Usuario> listaUsuarios = repositorio.buscar(criterio);
        filtros.clear();
        return listaUsuarios;
    }
}
