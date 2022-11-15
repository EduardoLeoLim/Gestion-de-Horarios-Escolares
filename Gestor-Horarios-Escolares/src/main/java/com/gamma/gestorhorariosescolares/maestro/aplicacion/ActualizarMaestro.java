package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.actualizar.ServicioActualizadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroId;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ServicioActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class ActualizarMaestro {

    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioActualizadorMaestro actualizadorMaestro;
    private final ServicioBuscador<Usuario> buscadorUsuario;
    private final ServicioActualizadorUsuario actualizadorUsuario;

    public ActualizarMaestro(ServicioBuscador<Maestro> buscadorMaestro, ServicioActualizadorMaestro actualizadorMaestro,
                             ServicioBuscador<Usuario> buscadorUsuario, ServicioActualizadorUsuario actualizadorUsuario) {
        this.buscadorMaestro = buscadorMaestro;
        this.actualizadorMaestro = actualizadorMaestro;
        this.buscadorUsuario = buscadorUsuario;
        this.actualizadorUsuario = actualizadorUsuario;
    }

    public void actualizar(MaestroData maestroData) throws RecursoNoEncontradoException, NoPersonalDuplicadoException, UsuarioDuplicadoException, FormatoInvalidoException {
        if (maestroData == null)
            throw new NullPointerException();

        List<Maestro> listaBusquedaMaestro;

        //Validar si el maestro esta registrado
        listaBusquedaMaestro = buscadorMaestro
                .igual("id", String.valueOf(maestroData.id()))
                .igual("idUsuario", String.valueOf(maestroData.usuario().id()))
                .buscar();
        if (listaBusquedaMaestro.isEmpty())
            throw new RecursoNoEncontradoException("El maestro no se encuentra registrado en el sistema");

        //Validar disponibilidad de número de personal
        listaBusquedaMaestro = buscadorMaestro
                .igual("noPersonal", maestroData.noPersonal())
                .diferente("id", String.valueOf(maestroData.id()))
                .buscar();
        if (!listaBusquedaMaestro.isEmpty())
            throw new NoPersonalDuplicadoException("Ya hay un maestro registrado con el número de personal "
                    + maestroData.noPersonal());

        //El correo electrónico no es ocupado por otro usuario
        List<Usuario> listaBusquedaUsuario = buscadorUsuario
                .igual("correoElectronico", maestroData.usuario().correoElectronico())
                .diferente("id", String.valueOf(maestroData.usuario().id()))
                .buscar();
        if (listaBusquedaUsuario.size() > 0)
            throw new UsuarioDuplicadoException("Ya hay un usario registrado con ese correo electrónico "
                    + maestroData.usuario().correoElectronico());

        //Preparando datos para actualizar
        Maestro maestro = new Maestro(new MaestroId(maestroData.id()), maestroData.estatus(), maestroData.noPersonal(),
                maestroData.nombre(), maestroData.apellidoPaterno(), maestroData.apellidoMaterno(),
                maestroData.usuario().id());

        UsuarioData usuarioData = maestroData.usuario();
        Usuario usuario = new Usuario(usuarioData.id(), usuarioData.telefono(), usuarioData.correoElectronico(),
                usuarioData.claveAcceso(), usuarioData.tipo());

        //Actualizando datos
        actualizadorUsuario.actualizar(usuario);
        actualizadorMaestro.actualizar(maestro);
    }
}
