package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarSecretarios {

    private final ServicioBuscador<Secretario> buscadorSecretario;
    private final ServicioBuscador<Usuario> buscadorUsuario;

    public BuscarSecretarios(ServicioBuscador<Secretario> buscadorSecretario, ServicioBuscador<Usuario> buscadorUsuario) {
        this.buscadorSecretario = buscadorSecretario;
        this.buscadorUsuario = buscadorUsuario;
    }

    public SecretariosData buscarTodos() {
        List<Secretario> listaSecretarios = buscadorSecretario
                .ordenarAscendente("noPersonal")
                .buscar();
        List<SecretarioData> listaSecretariosData = listaSecretarios.stream().map(secretario -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(secretario.idUsuario())).buscar().get(0);
            return SecretarioData.fromAggregate(secretario, usuario);
        }).collect(Collectors.toList());

        return new SecretariosData(listaSecretariosData);
    }

    public SecretariosData buscarHabilitados() {
        List<Secretario> listaSecretarios = buscadorSecretario
                .igual("estatus", "1")
                .ordenarAscendente("noPersonal")
                .buscar();// '1' = true, '0' = false

        List<SecretarioData> listaSecretariosData = listaSecretarios.stream().map(secretario -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(secretario.idUsuario())).buscar().get(0);
            return SecretarioData.fromAggregate(secretario, usuario);
        }).collect(Collectors.toList());

        return new SecretariosData(listaSecretariosData);
    }

    public SecretariosData buscarPorCriterio(String criterio) {
        List<Secretario> listaSecretarios = buscadorSecretario
                .contiene("noPersonal", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("apellidoPaterno", criterio).esOpcional()
                .contiene("apellidoMaterno", criterio).esOpcional()
                .buscar();

        List<SecretarioData> listaSecretariosData = listaSecretarios.stream().map(secretario -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(secretario.idUsuario())).buscar().get(0);
            return SecretarioData.fromAggregate(secretario, usuario);
        }).collect(Collectors.toList());

        return new SecretariosData(listaSecretariosData);
    }
}
