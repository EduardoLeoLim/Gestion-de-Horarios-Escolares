package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.MatriculaDuplicadaException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar.ServicioRegistradorAlumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class RegistrarAlumno {
    private final ServicioRegistradorAlumno registradorAlumno;
    private final ServicioBuscador<Alumno> buscadorAlumno;
    private final ServicioRegistradorUsuario registradorUsuario;
    private final ServicioBuscador<Usuario> buscadorUsuario;

    public RegistrarAlumno(ServicioRegistradorAlumno registradorAlumno, ServicioBuscador<Alumno> buscadorAlumno,
                           ServicioRegistradorUsuario registradorUsuario, ServicioBuscador<Usuario> buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.registradorAlumno = registradorAlumno;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void registrar(String nombre, String appellidoPaterno, String apellidoMaterno, String curp, String matricula,
                          String telefono, String correoElectronico, String claveAcceso) throws CurpDuplicadoException, UsuarioDuplicadoException, MatriculaDuplicadaException {
        //Validar curp duplicado
        if (buscadorAlumno.igual("curp", curp).buscar().size() > 0)
            throw new CurpDuplicadoException();
        //Validar usuario duplicado
        if (buscadorUsuario.igual("correoElectronico", correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException();

        //Registrar usuario y obtener su id
        int idUsuario = registradorUsuario.registrar(telefono, correoElectronico, claveAcceso, "Alumno");

        //Registrar alumno con añadiendo id de usuario
        registradorAlumno.registrar(matricula, curp, nombre, appellidoPaterno, apellidoMaterno, idUsuario);
    }
}
