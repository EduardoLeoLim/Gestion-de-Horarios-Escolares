package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.ServicioBuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar.ServicioRegistradorAlumno;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.ServicioBuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;

public class RegistrarAlumno {
    private final ServicioRegistradorAlumno registradorAlumno;
    private final ServicioBuscadorAlumno buscadorAlumno;
    private final ServicioRegistradorUsuario registradorUsuario;
    private final ServicioBuscadorUsuario buscadorUsuario;

    public RegistrarAlumno(ServicioRegistradorAlumno registradorAlumno, ServicioBuscadorAlumno buscadorAlumno,
                           ServicioRegistradorUsuario registradorUsuario, ServicioBuscadorUsuario buscadorUsuario) {
        this.buscadorAlumno = buscadorAlumno;
        this.registradorAlumno = registradorAlumno;
        this.buscadorUsuario = buscadorUsuario;
        this.registradorUsuario = registradorUsuario;
    }

    public void Registrar(String nombre, String appellidoPaterno, String apellidoMaterno, String curp, String matricula,
                          String correoElectronico, String claveAcceso) throws CurpDuplicadoException, UsuarioDuplicadoException {
        //Validar curp duplicado
        if (buscadorAlumno.igual("curp", curp).buscar().size() > 0 )
            throw new CurpDuplicadoException();
        //Validar usuario duplicado
        if (buscadorUsuario.filtarCorreo(correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException();

        //Registrar usuario y obtener su id
        int idUsuario = registradorUsuario.registrar(correoElectronico, claveAcceso);

        //Registrar alumno con añadiendo id de usuario
        registradorAlumno.registrar(matricula, curp, nombre, appellidoPaterno, apellidoMaterno, idUsuario);
    }
}
