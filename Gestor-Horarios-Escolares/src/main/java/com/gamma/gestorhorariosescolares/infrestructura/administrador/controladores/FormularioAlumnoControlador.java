package com.gamma.gestorhorariosescolares.infrestructura.administrador.controladores;

import com.gamma.gestorhorariosescolares.aplicacion.alumno.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.aplicacion.alumno.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.aplicacion.alumno.registrar.RegistradorAlumno;
import com.gamma.gestorhorariosescolares.aplicacion.alumno.registrar.RegistrarAlumno;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.registrar.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.aplicacion.usuario.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.infrestructura.alumno.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.infrestructura.compartido.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.infrestructura.usuario.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class FormularioAlumnoControlador {
    @FXML
    protected void guardarClick() {
        //Datos válidos del formulario

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //codigo de inicialización, las clases repositorio reciben conn en su constructor
            //UsuarioRepositorio usuarioRepositorio = new UsusarioRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(transaccion);

            var registradorAlumno = new RegistradorAlumno(alumnoRepositorio);
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var registradorUsuario = new RegistradorUsuario(usuarioRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            new RegistrarAlumno(registradorAlumno, buscadorAlumno, registradorUsuario, buscadorUsuario)
                    .Registrar("", "", "", "", "", "", "");

            transaccion.commit();//Guardar cambios en base de datos
            System.out.println("Alumno registrado");
        } catch (CurpDuplicadoException | UsuarioDuplicadoException exception){
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
    }
}
