package com.gamma.gestorhorariosescolares.grado.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Order;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grado.dominio.GradoRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuscadorGrado implements ServicioBuscador<Grado> {

    private final GradoRepositorio repositorio;
    private final List<Filter> filtros;
    private Order ordenador;
    private Optional<Integer> intervalo;
    private Optional<Integer> limite;

    public BuscadorGrado(GradoRepositorio repositorio) {
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
    public ServicioBuscador<Grado> igual(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el valor debe ser diferente al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> diferente(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el valor debe ser mayor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> mayorQue(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el valor debe ser mayor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> mayorIgualQue(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el valor debe ser menor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> menorQue(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el valor debe ser menor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> menorIgualQue(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el campo contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> contiene(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro, en donde el campo no contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> noContiene(String campo, String valor) {
        return null;
    }

    /**
     * Define filtro para ordenar de forma ascendente
     *
     * @param campo Campo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> ordenarAscendente(String campo) {
        return null;
    }

    /**
     * Define filtro para ordenar de forma descendente
     *
     * @param campo CCampo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> ordenarDescendente(String campo) {
        return null;
    }

    /**
     * Define al último filtro agregado como obligatorio
     *
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> esObligatorio() {
        return null;
    }

    /**
     * Define al último filtro agregado como opcional
     *
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> esOpcional() {
        return null;
    }

    /**
     * Define cuantos recursos serán omitidos al principio de la búsqueda
     *
     * @param intervalo Cantidad de recursos que serán omitidos
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> intervalo(int intervalo) {
        return null;
    }

    /**
     * Define la cantidad de recursos que se obtendrán
     *
     * @param limite Cantidad de recursos que se obtendrán
     * @return ServicioBuscador
     */
    @Override
    public ServicioBuscador<Grado> limite(int limite) {
        return null;
    }

    /**
     * Busca los recursos
     *
     * @return Lista de recursos que cumplan los filtros definidos
     */
    @Override
    public List<Grado> buscar() {
        return null;
    }

    /**
     * Busca el primer recurso que cumpla con los filtros
     *
     * @return Un recurso
     * @throws RecursoNoEncontradoException Si no se encontró algún recurso
     */
    @Override
    public Grado buscarPrimero() throws RecursoNoEncontradoException {
        return null;
    }
}
