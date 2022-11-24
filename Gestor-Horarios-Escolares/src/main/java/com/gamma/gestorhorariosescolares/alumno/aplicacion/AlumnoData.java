package com.gamma.gestorhorariosescolares.alumno.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;

public class AlumnoData {
    private final int id;
    private final String matricula;
    private final String curp;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final Boolean estatus;
    private final UsuarioData usuario;

    public AlumnoData(int id, String matricula, String curp, String nombre, String apellidoPaterno, String apellidoMaterno,
                      Boolean estatus, UsuarioData usuario) {
        this.id = id;
        this.matricula = matricula;
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.estatus = estatus;
        this.usuario = usuario;
    }

    public static AlumnoData fromAggregate(Alumno alumno, Usuario usuario) {
        UsuarioData usuarioData = UsuarioData.fromAggregate(usuario);
        return new AlumnoData(alumno.id(), alumno.matricula(), alumno.curp(), alumno.nombre(), alumno.apellidoPaterno(),
                alumno.apellidoMaterno(), alumno.estaHabilitado(), usuarioData);
    }

    public Integer id() {
        return id;
    }

    public String matricula() {
        return matricula;
    }

    public String curp() {
        return curp;
    }

    public String nombre() {
        return nombre;
    }

    public String apellidoPaterno() {
        return apellidoPaterno;
    }

    public String apellidoMaterno() {
        return apellidoMaterno;
    }

    public Boolean estatus() {
        return estatus;
    }

    public UsuarioData usuario() {
        return usuario;
    }
}
