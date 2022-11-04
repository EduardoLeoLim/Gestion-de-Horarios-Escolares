package com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar;

/**
 * Interface para iumplementarlas en clases que registren administradores
 *
 * @author Angel Eduardo Martínez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ServicioRegistradorAdministrador {

    /**
     * Registrar administrador
     *
     * @param noPersonal      El parámetro noPersonal define el numero de personal del administrador por ser un empleado
     * @param nombre          El parámetro nombre define el nombre del administrador
     * @param apellidoPaterno El parámetro apellidoPaterno corresponde al apellido paterno del administrador
     * @param apellidoMaterno El parámetor apellidoMaterno corresponde al apellido materno del administrador
     * @param idUsuario       El parámetro idUsuario corresponde al identificador del usuario del administrador
     */
    int registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario);

}