package com.gamma.gestorhorariosescolares.aplicacion.alumno.registrar;

import com.gamma.gestorhorariosescolares.aplicacion.alumno.buscar.ServicioBuscadorAlumno;
import com.gamma.gestorhorariosescolares.aplicacion.alumno.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.buscar.ServicioBuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.excepciones.UsuarioDuplicadoException;

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
        //Lanzar excepcion si no se cumplen la validaciones
        //Validar usuario duplicado
        //Lanzar excepcion si no se cumplen la validación

        if (buscadorAlumno.filtarCURP(curp).buscar().size() > 0 )
            throw new CurpDuplicadoException();
        if (buscadorUsuario.filtarCorreo(correoElectronico).buscar().size() > 0)
            throw new UsuarioDuplicadoException();

        //Registrar usuario y obtener su id
        int idUsuario = registradorUsuario.registrar(correoElectronico, claveAcceso);

        //Registrar alumno con añadiendo id de usuario
        registradorAlumno.registrar(matricula, curp, nombre, appellidoPaterno, apellidoMaterno, idUsuario);
    }
}
