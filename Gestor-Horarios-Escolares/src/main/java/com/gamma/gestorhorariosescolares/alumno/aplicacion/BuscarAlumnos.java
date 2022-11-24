package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarAlumnos {

    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ServicioBuscador<Usuario> buscadorUsuario;

    public BuscarAlumnos(ServicioBuscador<Alumno> buscadorAlumno, ServicioBuscador<Usuario> buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.buscadorUsuario = buscadorUsuario;
    }

    public AlumnosData buscarTodos() {
        List<Alumno> listaAlumnos = buscadorAlumno
                .ordenarAscendente("matricula")
                .buscar();
        List<AlumnoData> listaAlumnosData = listaAlumnos.stream().map(alumno -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(alumno.idUsuario())).buscar().get(0);
            return AlumnoData.fromAggregate(alumno, usuario);
        }).collect(Collectors.toList());

        return new AlumnosData(listaAlumnosData);
    }

    public AlumnosData buscarHabilitados() {
        List<Alumno> listaAlumnos = buscadorAlumno
                .igual("estatus", "1")
                .ordenarAscendente("matricula")
                .buscar();

        List<AlumnoData> listaAlumnosData = listaAlumnos.stream().map(alumno -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(alumno.idUsuario())).buscar().get(0);
            return AlumnoData.fromAggregate(alumno, usuario);
        }).collect(Collectors.toList());
        return new AlumnosData(listaAlumnosData);
    }

    public AlumnosData buscarPorCriterio(String criterio) {
        List<Alumno> listaAlumnos = buscadorAlumno
                .contiene("matricula", criterio).esOpcional()
                .contiene("curp", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .contiene("apellidoPaterno", criterio).esOpcional()
                .contiene("apellidoMaterno", criterio).esOpcional()
                .ordenarAscendente("matricula")
                .buscar();

        List<AlumnoData> listaAlumnosData = listaAlumnos.stream().map(alumno -> {
            Usuario usuario = buscadorUsuario.igual("id", String.valueOf(alumno.idUsuario())).buscar().get(0);
            return AlumnoData.fromAggregate(alumno, usuario);
        }).collect(Collectors.toList());
        return new AlumnosData(listaAlumnosData);
    }

    public AlumnoData buscarPorUsuario(Integer idUsuario) throws RecursoNoEncontradoException {

        Alumno alumno = buscadorAlumno.igual("idUsuario", idUsuario.toString()).buscarPrimero();
        Usuario usuario = buscadorUsuario.igual("id", String.valueOf(alumno.idUsuario())).buscar().get(0);
        return AlumnoData.fromAggregate(alumno, usuario);

    }
}
