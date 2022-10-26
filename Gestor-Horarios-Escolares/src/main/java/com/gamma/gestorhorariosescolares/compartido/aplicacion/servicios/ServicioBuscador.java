package com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios;

import java.util.List;

/**
 * Interfaz que define los filtros para busaar un recurso
 *
 * @author Angel Eduardo Martínez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 * @param <T> Tipo de recurso que se quiere buscar
 */
public interface ServicioBuscador<T> {

    /**
     * Define filtro, en donde el valor debe ser igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> igual(String campo, String valor);

    /**
     * Define filtro, en donde el valor debe ser diferente al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> diferente(String campo, String valor);

    /**
     * Define filtro, en donde el valor debe ser mayor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> mayorQue(String campo, String valor);

    /**
     * Define filtro, en donde el valor debe ser mayor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> mayorIgualQue(String campo, String valor);

    /**
     * Define filtro, en donde el valor debe ser menor al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> menorQue(String campo, String valor);

    /**
     * Define filtro, en donde el valor debe ser menor o igual al campo
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> menorIgualQue(String campo, String valor);

    /**
     * Define filtro, en donde el campo contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> contiene(String campo, String valor);

    /**
     * Define filtro, en donde el campo no contiene el valor
     *
     * @param campo Campo que se desea comprobar
     * @param valor Valor que se utiliza para hacer la comprobación
     * @return ServicioBuscador
     */
    ServicioBuscador<T> noContiene(String campo, String valor);

    /**
     * Define filtro para ordenar de forma ascendente
     *
     * @param campo Campo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    ServicioBuscador<T> ordenarAscendente(String campo);

    /**
     * Define filtro para ordenar de forma descendente
     *
     * @param campo CCampo que se utiliza para ordenar el resultado de la búsqueda
     * @return ServicioBuscador
     */
    ServicioBuscador<T> ordenarDescendente(String campo);

    /**
     * Define al último filtro agregado como opcional
     *
     * @return ServicioBuscador
     */
    ServicioBuscador<T> esObligatorio();

    /**
     * Define al último filtro agregado como obligatorio
     *
     * @return ServicioBuscador
     */
    ServicioBuscador<T> esOpcional();

    /**
     * Define cuantos recursos serán omitidos al principio de la búsqueda
     *
     * @param intervalo Cantidad de recursos que serán omitidos
     * @return ServicioBuscador
     */
    ServicioBuscador<T> intervalo(int intervalo);

    /**
     * Define la cantidad de recursos que se obtendrán
     *
     * @param limite Cantidad de recursos que se obtendrán
     * @return ServicioBuscador
     */
    ServicioBuscador<T> limite(int limite);

    /**
     * Busca los recursos
     *
     * @return Lista de recursos que cumplan los filtros definidos
     */
    List<T> buscar();
}
