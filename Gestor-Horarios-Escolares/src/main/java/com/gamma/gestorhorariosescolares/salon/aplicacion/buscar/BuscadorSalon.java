package com.gamma.gestorhorariosescolares.salon.aplicacion.buscar;

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

    @Override
    public BuscadorSalon igual(String campo, String valor) {
        filtros.add(Filter.create(campo, "=", valor));
        return this;
    }

    @Override
    public BuscadorSalon diferente(String campo, String valor) {
        filtros.add(Filter.create(campo, "!=", valor));
        return this;
    }

    @Override
    public BuscadorSalon mayorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">", valor));
        return this;
    }

    @Override
    public BuscadorSalon mayorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, ">=", valor));
        return this;
    }

    @Override
    public BuscadorSalon menorQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<", valor));
        return this;
    }

    @Override
    public BuscadorSalon menorIgualQue(String campo, String valor) {
        filtros.add(Filter.create(campo, "<=", valor));
        return this;
    }

    @Override
    public BuscadorSalon contiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorSalon noContiene(String campo, String valor) {
        filtros.add(Filter.create(campo, "NOT_CONTAINS", valor));
        return this;
    }

    @Override
    public BuscadorSalon ordenarAscendente(String campo) {
        ordenador = Order.asc(campo);
        return this;
    }

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

    @Override
    public BuscadorSalon intervalo(int intervalo) {
        this.intervalo = Optional.of(intervalo);
        return this;
    }

    @Override
    public BuscadorSalon limite(int limite) {
        this.limite = Optional.of(limite);
        return this;
    }

    @Override
    public List<Salon> buscar() {
        Criteria criterio = new Criteria(new Filters(filtros), ordenador, intervalo, limite);
        List<Salon> listaSalones = repositorio.buscar(criterio);
        //Resetaer para reutilizar instacia
        filtros.clear();
        ordenador = Order.none();
        intervalo = Optional.empty();
        limite = Optional.empty();
        return listaSalones;
    }
}
