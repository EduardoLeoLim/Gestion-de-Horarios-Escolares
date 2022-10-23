package com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;

/**
 * Clase que permite registrar un Administrador
 *
 * @author Angel Eduardo Martinez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegistradorAdministrador implements ServicioRegistradorAdministrador {

    private final AdministradorRepositorio repositorio;

    /**
     * Constructor de RegistradorAdministrador
     *
     * @param repositorio El parametro repositorio es la instancia encarda de conectarse con la base de datos
     */
    public RegistradorAdministrador(AdministradorRepositorio repositorio) {
        this.repositorio = repositorio;
    }


    /**
     * Registrar administrador
     *
     * @param noPersonal      El parámetro noPersonal define el numero de personal del administrador por ser un empleado
     * @param nombre          El parámetro nombre define el nombre del administrador
     * @param apellidoPaterno El parámetro apellidoPaterno corresponde al apellido paterno del administrador
     * @param apellidoMaterno El parámetor apellidoMaterno corresponde al apellido materno del administrador
     * @param idUsuario       El parámetro idUsuario corresponde al identificador del usuario del administrador
     */
    @Override
    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        var administrador = new Administrador(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
        repositorio.registrar(administrador);
    }

}