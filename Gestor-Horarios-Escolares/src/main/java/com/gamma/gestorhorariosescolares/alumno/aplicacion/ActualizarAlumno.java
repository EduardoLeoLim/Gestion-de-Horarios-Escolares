package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar.ServicioActualizadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.MatriculaDuplicadaException;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ServicioActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

import java.util.List;

public class ActualizarAlumno {

    private final ServicioActualizadorAlumno actualizadorAlumno;
    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ServicioActualizadorUsuario actualizadorUsuario;
    private final ServicioBuscador<Usuario> buscadorUsuario;

    public ActualizarAlumno(ServicioActualizadorAlumno actualizadorAlumno, ServicioBuscador<Alumno> buscadorAlumno,
                            ServicioActualizadorUsuario actualizadorUsuario, ServicioBuscador<Usuario> buscadorUsuario) {
        this.actualizadorAlumno = actualizadorAlumno;
        this.buscadorAlumno = buscadorAlumno;
        this.actualizadorUsuario = actualizadorUsuario;
        this.buscadorUsuario = buscadorUsuario;
    }

    private void actualizar(AlumnoData alumnoData) throws RecursoNoEncontradoException, MatriculaDuplicadaException, CurpDuplicadoException, UsuarioDuplicadoException {
        if (alumnoData == null)
            throw new NullPointerException();

        List<Alumno> listaBusquedaAlumno;

        //Validar si el alumno esta registrador en el sistema
        listaBusquedaAlumno = buscadorAlumno
                .igual("id", String.valueOf(alumnoData.id()))
                .igual("idUsuario", String.valueOf(alumnoData.usuario().id()))
                .buscar();
        if (!(listaBusquedaAlumno.size() > 0))
            throw new RecursoNoEncontradoException("El alumno no se encuentra registrado en el sistema");

        //Validar que no haya otro alumno con la matrucula
        listaBusquedaAlumno = buscadorAlumno
                .igual("matricula", alumnoData.matricula())
                .diferente("id", String.valueOf(alumnoData.id()))
                .buscar();
        if (listaBusquedaAlumno.size() > 0)
            throw new MatriculaDuplicadaException("Ya hay un alumno registrado con la matrícula " + alumnoData.matricula());

        //Validar que no haya otro alumno con el curp
        listaBusquedaAlumno = buscadorAlumno
                .igual("curp", alumnoData.curp())
                .diferente("id", String.valueOf(alumnoData.id()))
                .buscar();
        if (listaBusquedaAlumno.size() > 0)
            throw new CurpDuplicadoException("Ya hay un alumno registrado con ese CURP");

        //El correo electrónico no es ocupado por otro usuario
        List<Usuario> listaBusquedaUsuario = buscadorUsuario
                .igual("correoElectronico", alumnoData.usuario().correoElectronico())
                .diferente("id", String.valueOf(alumnoData.usuario().id()))
                .buscar();
        if (listaBusquedaUsuario.size() > 0)
            throw new UsuarioDuplicadoException("Ya hay un usario registrado con ese correo electrónico");

        //Preparando datos para actualizar
        Alumno alumno = new Alumno(alumnoData.id(), alumnoData.estatus(), alumnoData.matricula(), alumnoData.curp(),
                alumnoData.nombre(), alumnoData.apellidoPaterno(), alumnoData.apellidoMaterno(), alumnoData.usuario().id());

        UsuarioData usuarioData = alumnoData.usuario();
        Usuario usuario = new Usuario(usuarioData.id(), usuarioData.telefono(), usuarioData.correoElectronico(),
                usuarioData.claveAcceso(), usuarioData.tipo());

        actualizadorUsuario.actualizar(usuario);
        actualizadorAlumno.actualizar(alumno);
    }

}