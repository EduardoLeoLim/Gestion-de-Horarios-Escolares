package com.gamma.gestorhorariosescolares.administrador.dominio;

/**
 * Clase que define la informacion de los Administradores en el sistema
 *
 * @author Angel Eduardo Martínez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public class Administrador {

    private final int id;
    private final String noPersonal;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final int idUsuario;
    private boolean estatus;

    /**
     * Constructor para un Administrador registrado en el sistema
     *
     * @param id              El parámetro id define el identificador del administrador en la base de datos
     * @param estatus         El parámetro estatus define el estatus del administrador dentro del sistema
     * @param noPersonal      El parámetro noPersonal define el numero de personal del administrador por ser un empleado
     * @param nombre          El parámetro nombre define el nombre del administrador
     * @param apellidoPaterno El parámetro apellidoPaterno corresponde al apellido paterno del administrador
     * @param apellidoMaterno El parámetor apellidoMaterno corresponde al apellido materno del administrador
     * @param idUsuario       El parámetro idUsuario corresponde al identificador del usuario del administrador
     */
    public Administrador(int id, boolean estatus, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        this.id = id;
        this.estatus = estatus;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    /**
     * Constructor para un administrador que será registrado en el sistema
     *
     * @param noPersonal      El parámetro noPersonal define el numero de personal del administrador por ser un empleado
     * @param nombre          El parámetro nombre define el nombre del administrador
     * @param apellidoPaterno El parámetro apellidoPaterno corresponde al apellido paterno del administrador
     * @param apellidoMaterno El parámetor apellidoMaterno corresponde al apellido materno del administrador
     * @param idUsuario       El parámetro idUsuario corresponde al identificador del usuario del administrador
     */
    public Administrador(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, int idUsuario) {
        id = 0;
        estatus = true;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idUsuario = idUsuario;
    }

    /**
     * Obtener identificador del administrador
     *
     * @return Identificador del administrador
     */
    public int id() {
        return id;
    }

    /**
     * Obetener número del personal del administrador
     *
     * @return Número de personal del administrador
     */
    public String noPersonal() {
        return noPersonal;
    }

    /**
     * Obtener nombre del administrador
     *
     * @return Nombre del administrador
     */
    public String nombre() {
        return nombre;
    }

    /**
     * Obtener apellido paterno del administraodor
     *
     * @return Apellido paterno del administradoor
     */
    public String apellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Obtener apellido materno del administrador
     *
     * @return Apellido materno del administrador
     */
    public String apellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Actualizar el estatus del administrador como habilitado
     */
    public void habilitar() {
        estatus = true;
    }

    /**
     * Actualizar el estatus del administrador como deshabilitado
     */
    public void deshabilitar() {
        estatus = false;
    }

    /**
     * Obtener el estatus del administrador
     *
     * @return Estatus del administrador
     */
    public boolean estatus() {
        return estatus;
    }

    /**
     * Obtener identificador de usuario del administrador
     *
     * @return Identificador de usaurio del administrador
     */
    public int idUsuario() {
        return idUsuario;
    }

}