package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
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
}
