package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.registrar.ServicioRegistradorSecretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class RegistrarSecretario {

    private final ServicioBuscador<Secretario> buscadorSecretario;
    private final ServicioRegistradorSecretario registradorSecretario;
    private final ServicioBuscador<Usuario> buscadorUsuario;
    private final ServicioRegistradorUsuario registradorUsuario;

    public RegistrarSecretario(ServicioBuscador<Secretario> buscadorSecretario,
                               ServicioRegistradorSecretario registradorSecretario,
                               ServicioBuscador<Usuario> buscadorUsuario,
                               ServicioRegistradorUsuario registradorUsuario) {
        this.buscadorSecretario = buscadorSecretario;
        this.registradorSecretario = registradorSecretario;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono,
                          String correoElectronico, String claveAcceso) throws NoPersonalDuplicadoException, UsuarioDuplicadoException, FormatoInvalidoException {
        List<Secretario> listaBusquedaSecretario;

        //¿Ya hay un secretario registrado con el noPersonal?
        listaBusquedaSecretario = buscadorSecretario
                .igual("noPersonal", noPersonal)
                .buscar();
        if (!listaBusquedaSecretario.isEmpty())
            throw new NoPersonalDuplicadoException("Ya hay un secretario registrado con el número de personal " + noPersonal);

        //¿El correo electrónico esta disponible?
        List<Usuario> listaBusquedaUsuario = buscadorUsuario
                .igual("correoElectronico", correoElectronico)
                .buscar();
        if (!listaBusquedaSecretario.isEmpty())
            throw new UsuarioDuplicadoException("Ya hay un usuario con el correo electrónico " + correoElectronico);

        int idUsuario = registradorUsuario.registrar(telefono, correoElectronico, claveAcceso, "Secretario");
        registradorSecretario.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
    }
}
