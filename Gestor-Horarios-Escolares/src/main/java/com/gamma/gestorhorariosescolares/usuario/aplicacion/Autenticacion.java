package com.gamma.gestorhorariosescolares.usuario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class Autenticacion {

    private final ServicioBuscador<Usuario> buscadorUsuario;

    public Autenticacion(ServicioBuscador<Usuario> buscadorUsuario) {
        this.buscadorUsuario = buscadorUsuario;
    }

    public UsuarioData ingresar(String correoElectronico, String claveAcceso) throws RecursoNoEncontradoException {
        List<Usuario> usuarios = buscadorUsuario
                .igual("correoElectronico", correoElectronico)
                .igual("claveAcceso", claveAcceso)
                .buscar();

        //¿Se encotro al usuario?
        if (usuarios.isEmpty())
            throw new RecursoNoEncontradoException("Usuario no registrado en el sistema");

        Usuario usuario = usuarios.get(0);
        return UsuarioData.fromAggregate(usuario);
    }
}
