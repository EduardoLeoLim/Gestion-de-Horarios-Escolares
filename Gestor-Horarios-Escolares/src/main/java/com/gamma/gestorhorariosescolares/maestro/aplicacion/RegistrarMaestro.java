package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.registrar.ServicioRegistradorMaestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class RegistrarMaestro {

    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioRegistradorMaestro registradorMaestro;
    private final ServicioBuscador<Usuario> buscadorUsuario;
    private final ServicioRegistradorUsuario registradorUsuario;

    public RegistrarMaestro(ServicioBuscador<Maestro> buscadorMaestro, ServicioRegistradorMaestro registradorMaestro,
                            ServicioBuscador<Usuario> buscadorUsuario, ServicioRegistradorUsuario registradorUsuario) {
        this.buscadorMaestro = buscadorMaestro;
        this.registradorMaestro = registradorMaestro;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                          String telefono, String correoElectronico, String claveAcceso)
            throws NoPersonalDuplicadoException, UsuarioDuplicadoException {
        List<Maestro> listaBusquedaMaestro;
        List<Usuario> listaBusquedaUsuario;

        //¿Hay un maestro registrado con el número de personal?
        listaBusquedaMaestro = buscadorMaestro
                .igual("noPersonal", noPersonal)
                .buscar();
        if (!listaBusquedaMaestro.isEmpty())
            throw new NoPersonalDuplicadoException("Ya hay un maestro registrado con el número de personal " + noPersonal);

        //¿Hay un usuario registrado con el correo electrónico?
        listaBusquedaUsuario = buscadorUsuario
                .igual("correoElectronico", correoElectronico)
                .buscar();
        if (!listaBusquedaUsuario.isEmpty())
            throw new UsuarioDuplicadoException("Ya hay un usuario registrado con el correo electrónico "
                    + correoElectronico);

        int idUsuario = registradorUsuario.registrar(telefono, correoElectronico, claveAcceso, "Maestro");
        registradorMaestro.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
    }
}
