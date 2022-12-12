package com.gamma.gestorhorariosescolares.maestro.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarMaestros {

    private final ServicioBuscador<Maestro> buscadorMaestro;
    private final ServicioBuscador<Usuario> buscadorUsuario;

    public BuscarMaestros(ServicioBuscador<Maestro> buscadorMaestro, ServicioBuscador<Usuario> buscadorUsuario) {
        this.buscadorMaestro = buscadorMaestro;
        this.buscadorUsuario = buscadorUsuario;
    }

    public MaestroData buscarPorUsuario(Integer idUsuario) throws RecursoNoEncontradoException {

        Maestro maestro = buscadorMaestro.igual("idUsuario", idUsuario.toString()).buscarPrimero();
        Usuario usuario = buscadorUsuario.igual("id", String.valueOf(maestro.idUsuario())).buscar().get(0);
        return MaestroData.fromAggregate(maestro, usuario);

    }

    public MaestrosData buscarTodos() {
        List<Maestro> listaMaestros = buscadorMaestro
                .ordenarAscendente("noPersonal")
                .buscar();

        List<MaestroData> listaMaestrosData = listaMaestros.stream().map(maestro -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(maestro.idUsuario())).buscar().get(0);
            return MaestroData.fromAggregate(maestro, usuario);
        }).collect(Collectors.toList());

        return new MaestrosData(listaMaestrosData);
    }

    public MaestrosData buscarHabilidatos() {
        List<Maestro> listaMaestros = buscadorMaestro
                .igual("estatus", "1")
                .ordenarAscendente("noPersonal")
                .buscar();

        List<MaestroData> listaMaestrosData = listaMaestros.stream().map(maestro -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(maestro.idUsuario())).buscar().get(0);
            return MaestroData.fromAggregate(maestro, usuario);
        }).collect(Collectors.toList());

        return new MaestrosData(listaMaestrosData);
    }

    public MaestrosData buscarPorCriterio(String criterio) {
        List<Maestro> listaMaestros = buscadorMaestro
                .contiene("noPersonal", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("apellidoPaterno", criterio).esOpcional()
                .contiene("apellidoMaterno", criterio).esOpcional()
                .ordenarAscendente("noPersonal")
                .buscar();

        List<MaestroData> listaMaestrosData = listaMaestros.stream().map(maestro -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(maestro.idUsuario())).buscar().get(0);
            return MaestroData.fromAggregate(maestro, usuario);
        }).collect(Collectors.toList());

        return new MaestrosData(listaMaestrosData);
    }

    public MaestrosData buscarPorCriterioHabilitados(String criterio) {
        List<Maestro> listaMaestros = buscadorMaestro
                .igual("estatus", "1")
                .contiene("noPersonal", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("apellidoPaterno", criterio).esOpcional()
                .contiene("apellidoMaterno", criterio).esOpcional()
                .ordenarAscendente("noPersonal")
                .buscar();

        List<MaestroData> listaMaestrosData = listaMaestros.stream().map(maestro -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(maestro.idUsuario())).buscar().get(0);
            return MaestroData.fromAggregate(maestro, usuario);
        }).collect(Collectors.toList());

        return new MaestrosData(listaMaestrosData);
    }

}