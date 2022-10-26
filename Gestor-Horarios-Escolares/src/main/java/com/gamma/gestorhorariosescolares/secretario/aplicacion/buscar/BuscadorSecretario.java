package com.gamma.gestorhorariosescolares.secretario.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorSecretario implements ServicioBuscador<Secretario> {

    private final SecretarioRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorSecretario(SecretarioRepositorio repositorio) {
        this.repositorio = repositorio;
        filtros = new ArrayList<>();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

    @Override
    public ServicioBuscador<Secretario> igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    /**
     * Define al último filtro agregado como opcional
     *
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Secretario> esObligatorio() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).obligatory();
        return this;
    }

    /**
     * Define al último filtro agregado como obligatorio
     *
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Secretario> esOpcional() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).optional();
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public ServicioBuscador<Secretario> limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Secretario> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Secretario> listaSecretarios = repositorio.buscar(criterio);
        //Resetaer para reutilizar instacia
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
        return listaSecretarios;
    }
}
