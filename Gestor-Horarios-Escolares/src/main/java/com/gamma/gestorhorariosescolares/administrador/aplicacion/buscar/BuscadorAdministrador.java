package com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;

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

    /**
     * Define filtro, en donde el valor debe ser igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    /**
     * Define filtro, en donde el valor debe ser diferente al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    /**
     * Define filtro, en donde el valor debe ser mayor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    /**
     * Define filtro, en donde el valor debe ser mayor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    /**
     * Define filtro, en donde el valor debe ser menor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    /**
     * Define filtro, en donde el valor debe ser menor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    /**
     * Define filtro, en donde el campo contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    /**
     * Define filtro, en donde el campo no contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    /**
     * Define filtro para ordenar de forma ascendente
     *
     * @param campo Campo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

    /**
     * Define filtro para ordenar de forma descendente
     *
     * @param campo CCampo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    /**
     * Define al último filtro agregado como opcional
     *
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador esObligatorio() {
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
    public BuscadorAdministrador esOpcional() {
        if (!filtros.isEmpty())
            filtros.get(filtros.size() - 1).optional();
        return this;
    }

    /**
     * Define cuantos recursos serán omitidos al principio de la búsqueda
     *
     * @param intervalo Cantidad de recursos que serán omitidos
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    /**
     * Define la cantidad de recursos que se obtendrán
     *
     * @param limite Cantidad de recursos que se obtendrán
     * @return ServicioBuscador
     */
    @Override
    public BuscadorAdministrador limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    /**
     * Busca los recursos
     *
     * @return Lista de recursos que cumplan los filtros definidos
     */
    @Override
    public List<Administrador> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Administrador> listaAdministradores = repositorio.buscar(criterio);
        //Resetaer para reutilizar instacia
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
        return listaAdministradores;
    }

}