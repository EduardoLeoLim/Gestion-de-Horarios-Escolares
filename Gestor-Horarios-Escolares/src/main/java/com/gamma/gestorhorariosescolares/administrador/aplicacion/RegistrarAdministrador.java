package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar.RegistradorAdministrador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;

public class RegistrarAdministrador {
    private final BuscadorAdministrador buscadorAdministrador;
    private final RegistradorAdministrador registradorAdministrador;
    private final BuscadorUsuario buscadorUsuario;
    private final RegistradorUsuario registradorUsuario;

    public RegistrarAdministrador(BuscadorAdministrador buscadorAdministrador, RegistradorAdministrador registradorAdministrador,
                                  BuscadorUsuario buscadorUsuario, RegistradorUsuario registradorUsuario) {
        this.buscadorAdministrador = buscadorAdministrador;
        this.registradorAdministrador = registradorAdministrador;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void registrar(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                          String correoElectronico, String claveAcceso) throws NoPersonalDuplicadoException, UsuarioDuplicadoException {
        if (buscadorAdministrador.filtrarNoPersonal(noPersonal).buscar().size() > 0)
            throw new NoPersonalDuplicadoException();
        if (buscadorUsuario.filtarCorreo(correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException();

        int idUsuario = registradorUsuario.registrar(correoElectronico, claveAcceso, "Administrador");
        registradorAdministrador.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
    }
}
