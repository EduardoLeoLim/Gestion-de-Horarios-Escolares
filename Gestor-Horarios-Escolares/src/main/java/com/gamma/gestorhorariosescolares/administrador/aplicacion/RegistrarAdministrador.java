package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar.RegistradorAdministrador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;

/**
 * Clase que permite registrar un Administrador con su usuario
 *
 * @author Angel Eduardo Martínez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegistrarAdministrador {
    private final BuscadorAdministrador buscadorAdministrador;
    private final RegistradorAdministrador registradorAdministrador;
    private final BuscadorUsuario buscadorUsuario;
    private final RegistradorUsuario registradorUsuario;

    /**
     * Constructor para RegistrarAdministrador
     *
     * @param buscadorAdministrador    El parámetro buscadorAdministrador permite buscar administradores
     * @param registradorAdministrador El parámetro registradorAdministrador permite registrar administradores
     * @param buscadorUsuario          El parámetro buscadorUsuario permite buscar usaurios
     * @param registradorUsuario       El parámeto registradorUsuarii permite registrar usuarios
     */
    public RegistrarAdministrador(BuscadorAdministrador buscadorAdministrador, RegistradorAdministrador registradorAdministrador,
                                  BuscadorUsuario buscadorUsuario, RegistradorUsuario registradorUsuario) {
        this.buscadorAdministrador = buscadorAdministrador;
        this.registradorAdministrador = registradorAdministrador;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    /**
     * Registrar Administrador, con un usuario, en el sistema.
     *
     * @param noPersonal        Número de personal único
     * @param nombre            Nombre del administrador
     * @param apellidoPaterno   Apellido paterno del administrador
     * @param apellidoMaterno   Apellido materno del administrador
     * @param telefono          Número telefonico del administrador
     * @param correoElectronico Correo electrónico del usaurio del administrador
     * @param claveAcceso       Clave de acceso del usuario del usuario del administrador
     * @throws NoPersonalDuplicadoException Ya hay un usaurio registrado con el número de personal
     * @throws UsuarioDuplicadoException    Ya hay un usuario registrado en el sistema
     */
    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono,
                          String correoElectronico, String claveAcceso) throws NoPersonalDuplicadoException, UsuarioDuplicadoException {
        if (buscadorAdministrador.igual("noPersonal", noPersonal).buscar().size() > 0)
            throw new NoPersonalDuplicadoException();
        if (buscadorUsuario.igual("correoElectronico", correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException();

        int idUsuario = registradorUsuario.registrar(telefono, correoElectronico, claveAcceso, "Administrador");
        registradorAdministrador.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
    }
}
