package com.gamma.gestorhorariosescolares.salon.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filters;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;
import com.gamma.gestorhorariosescolares.salon.dominio.SalonRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorSalon implements ServicioBuscador<Salon> {

    private final SalonRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorSalon(SalonRepositorio repositorio) {
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
    public BuscadorSalon igual(String campo, String valor) {
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
    public BuscadorSalon diferente(String campo, String valor) {
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
    public BuscadorSalon mayorQue(String campo, String valor) {
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
    public BuscadorSalon mayorIgualQue(String campo, String valor) {
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
    public BuscadorSalon menorQue(String campo, String valor) {
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
    public BuscadorSalon menorIgualQue(String campo, String valor) {
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
    public BuscadorSalon contiene(String campo, String valor) {
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
    public BuscadorSalon noContiene(String campo, String valor) {
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
    public BuscadorSalon ordenarAscendente(String campo) {
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
    public BuscadorSalon ordenarDescendente(String campo) {
        ordenador = Order.desc(campo);
        return this;
    }

    /**
     * Define al último filtro agregado como opcional
     *
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Salon> esObligatorio() {
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
    public ServicioBuscador<Salon> esOpcional() {
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
    public BuscadorSalon intervalo(int intervalo) {
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
    public BuscadorSalon limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    /**
     * Busca los recursos
     *
     * @return Lista de recursos que cumplan los filtros definidos
     */
    @Override
    public List<Salon> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, limite, intervalo);
        List<Salon> listaSalones = repositorio.buscar(criterio);

        limpiarFiltros();

        return listaSalones;
    }

    /**
     * Busca el primer recurso que cumpla con los filtros
     *
     * @return Un recurso
     * @throws RecursoNoEncontradoException Si no se encontró algún recurso
     */
    @Override
    public Salon buscarPrimero() throws RecursoNoEncontradoException {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, limite, intervalo);
        List<Salon> listaSalones = repositorio.buscar(criterio);

        limpiarFiltros();
        if (listaSalones.isEmpty())
            throw new RecursoNoEncontradoException("No se encontró ningún salón.");
        return listaSalones.get(0);
    }

    private void limpiarFiltros() {
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
    }

}