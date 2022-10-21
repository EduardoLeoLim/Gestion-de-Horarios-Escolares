package com.gamma.gestorhorariosescolares.administrador.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

/**
 * Clase repositoirio del Administrador
 *
 * @author Angel Eduardo Martinez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AdministradorRepositorio {
    /**
     * Buscar lista de Administraodores que cumplan el criterio especificado
     *
     * @param criterio El parámetro criterio define los filtros que los administradores deben cumplir al buscarlos
     * @return Lista de Administradores
     */
    List<Administrador> buscar(Criteria criterio);

    /**
     * Registrar nuevo administrador
     *
     * @param administrador El parametro administrador es el Administrador que se registrará
     * @return Identificador asignado al administrador al ser registrado
     */
    int registrar(Administrador administrador);

    /**
     * Actualizar el administrador
     *
     * @param administrador El parametro administrador es el Administrador que se actualizará
     */
    void actualizar(Administrador administrador);
}
