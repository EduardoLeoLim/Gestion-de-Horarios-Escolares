package com.gamma.gestorhorariosescolares.administrador.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import org.sql2o.Connection;

import java.util.List;

public class MySql2oAdministradorRespositorio implements AdministradorRepositorio {

    private final Connection conexion;

    public MySql2oAdministradorRespositorio(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Buscar lista de Administraodores que cumplan el criterio especificado
     *
     * @param criterio El parámetro criterio define los filtros que los administradores deben cumplir al buscarlos
     * @return Lista de Administradores
     */
    @Override
    public List<Administrador> buscar(Criteria criterio) {
        return null;
    }

    /**
     * Registrar nuevo administrador
     *
     * @param administrador El parametro administrador es el Administrador que se registrará
     * @return Identificador asignado al administrador al ser registrado
     */
    @Override
    public int registrar(Administrador administrador) {
        return 0;
    }

    /**
     * Actualizar el administrador
     *
     * @param administrador El parametro administrador es el Administrador que se actualizará
     */
    @Override
    public void actualizar(Administrador administrador) {

    }
}
