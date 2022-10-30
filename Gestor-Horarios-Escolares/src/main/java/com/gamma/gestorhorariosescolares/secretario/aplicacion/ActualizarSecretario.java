package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar.ServicioActualizadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ServicioActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class ActualizarSecretario {

    private final ServicioBuscador<Secretario> buscadorSecretario;
    private final ServicioActualizadorSecretario actualizadorSecretario;
    private final ServicioBuscador<Usuario> buscadorUsuario;
    private final ServicioActualizadorUsuario actualizadorUsuario;

    public ActualizarSecretario(ServicioBuscador<Secretario> buscadorSecretario,
                                ServicioActualizadorSecretario actualizadorSecretario,
                                ServicioBuscador<Usuario> buscadorUsuario,
                                ServicioActualizadorUsuario actualizadorUsuario) {
        this.buscadorSecretario = buscadorSecretario;
        this.actualizadorSecretario = actualizadorSecretario;
        this.buscadorUsuario = buscadorUsuario;
        this.actualizadorUsuario = actualizadorUsuario;
    }

    public void actualizar(SecretarioData secretarioData)
            throws RecursoNoEncontradoException, NoPersonalDuplicadoException, UsuarioDuplicadoException, FormatoInvalidoException {
        if (secretarioData == null)
            throw new NullPointerException();

        List<Secretario> listaBusquedaSecretario;

        //¿Existe el secretario?
        listaBusquedaSecretario = buscadorSecretario
                .igual("id", String.valueOf(secretarioData.id()))
                .igual("idUsuario", String.valueOf(secretarioData.usuario().id()))
                .buscar();
        if (listaBusquedaSecretario.isEmpty())
            throw new RecursoNoEncontradoException("El secretario no se encuentra registrado en el sistema");

        //¿El nuevo número de personal está disponible?
        listaBusquedaSecretario = buscadorSecretario
                .igual("noPersonal", secretarioData.noPersonal())
                .diferente("id", String.valueOf(secretarioData.id()))
                .buscar();
        if (!listaBusquedaSecretario.isEmpty())
            throw new NoPersonalDuplicadoException("Ya hay un secretario registrado con el número de personal "
                    + secretarioData.noPersonal());

        List<Usuario> listaBusquedaUsuario;

        //¿Esta disponible el correo electrónico?
        listaBusquedaUsuario = buscadorUsuario
                .igual("correoElectronico", secretarioData.usuario().correoElectronico())
                .diferente("id", String.valueOf(secretarioData.usuario().id()))
                .buscar();
        if (!listaBusquedaUsuario.isEmpty())
            throw new UsuarioDuplicadoException("Ya hay un usario registrado con ese correo electrónico "
                    + secretarioData.usuario().correoElectronico());

        //Preparando datos para actualizar
        Secretario secretario = new Secretario(secretarioData.id(), secretarioData.estatus(),
                secretarioData.noPersonal(), secretarioData.nombre(), secretarioData.apellidoPaterno(),
                secretarioData.apellidoMaterno(), secretarioData.usuario().id());

        UsuarioData usuarioData = secretarioData.usuario();
        Usuario usuario = new Usuario(usuarioData.id(), usuarioData.telefono(), usuarioData.correoElectronico(),
                usuarioData.claveAcceso(), usuarioData.tipo());

        //Actualizando
        actualizadorUsuario.actualizar(usuario);
        actualizadorSecretario.actualizar(secretario);
    }
}
