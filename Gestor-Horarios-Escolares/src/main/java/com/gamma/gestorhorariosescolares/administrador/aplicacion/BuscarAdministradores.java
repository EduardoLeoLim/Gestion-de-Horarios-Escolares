package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarAdministradores {

    private final BuscadorAdministrador buscadorAdministrador;
    private final BuscadorUsuario buscadorUsuario;

    public BuscarAdministradores(BuscadorAdministrador buscadorAdministrador, BuscadorUsuario buscadorUsuario) {
        this.buscadorAdministrador = buscadorAdministrador;
        this.buscadorUsuario = buscadorUsuario;
    }

    public AdministradoresData buscarTodos() {
        List<Administrador> listaAdministradores = buscadorAdministrador
                .ordenarAscendente("noPersonal")
                .buscar();
        List<AdministradorData> listaAdministradoresData = listaAdministradores.stream().map(administrador -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(administrador.idUsuario())).buscar().get(0);
            return AdministradorData.fromAggregate(administrador, usuario);
        }).collect(Collectors.toList());

        return new AdministradoresData(listaAdministradoresData);
    }

    public AdministradoresData buscarHabilitados() {
        List<Administrador> listaAdministradores = buscadorAdministrador
                .igual("estatus", "1")
                .ordenarAscendente("noPersonal")
                .buscar();// '1' = true, '0' = false

        List<AdministradorData> listaAdministradoresData = listaAdministradores.stream().map(administrador -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(administrador.idUsuario())).buscar().get(0);
            return AdministradorData.fromAggregate(administrador, usuario);
        }).collect(Collectors.toList());

        return new AdministradoresData(listaAdministradoresData);
    }

}