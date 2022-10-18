package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;

import java.util.stream.Collectors;

public class BuscarAdministradores {

    private final BuscadorAdministrador buscadorAdministrador;
    private final BuscadorUsuario buscadorUsuario;

    public BuscarAdministradores(BuscadorAdministrador buscadorAdministrador, BuscadorUsuario buscadorUsuario) {
        this.buscadorAdministrador = buscadorAdministrador;
        this.buscadorUsuario = buscadorUsuario;
    }

    public AdministradoresData buscarTodos() {
        var listaAdministradores = buscadorAdministrador.buscar();
        var listaAdministradoresData = listaAdministradores.stream().map(administrador -> {
            var usuario = buscadorUsuario.igual("id", String.valueOf(administrador.idUsuario())).buscar().get(0);
            return AdministradorData.fromAggregate(administrador, usuario);
        });

        return new AdministradoresData(listaAdministradoresData.collect(Collectors.toList()));
    }

}
