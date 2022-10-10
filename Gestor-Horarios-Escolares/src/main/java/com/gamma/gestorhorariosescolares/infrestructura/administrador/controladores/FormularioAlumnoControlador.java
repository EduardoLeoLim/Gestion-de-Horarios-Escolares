package com.gamma.gestorhorariosescolares.infrestructura.administrador.controladores;

import com.gamma.gestorhorariosescolares.aplicacion.alumno.*;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.ServicioBuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.dominio.alumno.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.dominio.usuario.UsuarioRepositorio;
import com.gamma.gestorhorariosescolares.infrestructura.alumno.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.infrestructura.usuario.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class FormularioAlumnoControlador {
    @FXML
    protected void guardarClick() {
        //Datos válidos del formulario

        String cadenaConexion = "jdbc:mysql://localhost:3306/gestor_escolar";
        Sql2o conexion = new Sql2o(cadenaConexion,"root", "contraseña");

        try (Connection conn = conexion.beginTransaction()) {
            //codigo de inicialización, las clases repositorio reciben conn en su constructor
            //UsuarioRepositorio usuarioRepositorio = new UsusarioRepositorio(conn);
            UsuarioRepositorio usuarioRepositorio = new MySql2oUsuarioRepositorio(conn);
            AlumnoRepositorio alumnoRepositorio = new MySql2oAlumnoRepositorio(conn);

            ServicioRegistradorAlumno registradorAlumno = new RegistradorAlumno(alumnoRepositorio);
            ServicioBuscadorAlumno buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            ServicioRegistradorUsuario registradorUsuario = new RegistradorUsuario(usuarioRepositorio);
            ServicioBuscadorUsuario buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            new RegistrarAlumno(registradorAlumno, buscadorAlumno, registradorUsuario, buscadorUsuario)
                    .Registrar();

            conn.commit();//Guardar cambios en base de datos
            System.out.println("Registros hechos");
        }
    }
}
