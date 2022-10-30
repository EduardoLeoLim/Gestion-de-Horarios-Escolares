package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar.ServicioRegistradorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

/**
 * Clase que permite registrar un Administrador con su usuario
 *
 * @author Angel Eduardo Martínez Leo Lim
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegistrarAdministrador {

    private final ServicioBuscador<Administrador> buscadorAdministrador;
    private final ServicioRegistradorAdministrador registradorAdministrador;
    private final ServicioBuscador<Usuario> buscadorUsuario;
    private final ServicioRegistradorUsuario registradorUsuario;

    /**
     * Constructor para RegistrarAdministrador
     *
     * @param buscadorAdministrador    El parámetro buscadorAdministrador permite buscar administradores
     * @param registradorAdministrador El parámetro registradorAdministrador permite registrar administradores
     * @param buscadorUsuario          El parámetro buscadorUsuario permite buscar usaurios
     * @param registradorUsuario       El parámeto registradorUsuarii permite registrar usuarios
     */
    public RegistrarAdministrador(ServicioBuscador<Administrador> buscadorAdministrador, ServicioRegistradorAdministrador registradorAdministrador,
                                  ServicioBuscador<Usuario> buscadorUsuario, ServicioRegistradorUsuario registradorUsuario) {
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
     * @param telefono          Número telefónico del administrador
     * @param correoElectronico Correo electrónico del usuario del administrador
     * @param claveAcceso       Clave de acceso del usuario del administrador
     * @throws NoPersonalDuplicadoException Ya hay un usuario registrado con el número de personal
     * @throws UsuarioDuplicadoException    Ya hay un usuario registrado en el sistema
     */
    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono,
                          String correoElectronico, String claveAcceso) throws NoPersonalDuplicadoException, UsuarioDuplicadoException, FormatoInvalidoException {
        if (buscadorAdministrador.igual("noPersonal", noPersonal).buscar().size() > 0)
            throw new NoPersonalDuplicadoException("Ya hay un administrador registrado con ese número de personal");
        if (buscadorUsuario.igual("correoElectronico", correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException("Hay una cuenta de usuario registrada con ese correo electrónico");

        int idUsuario = registradorUsuario.registrar(telefono, correoElectronico, claveAcceso, "Administrador");
        registradorAdministrador.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
    }

}