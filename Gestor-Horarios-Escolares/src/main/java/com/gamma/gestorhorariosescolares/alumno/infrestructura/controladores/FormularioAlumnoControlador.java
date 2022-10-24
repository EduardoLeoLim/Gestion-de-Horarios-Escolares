package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.RegistrarAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.MatriculaDuplicadaException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar.RegistradorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class FormularioAlumnoControlador {
    @FXML
    protected void guardarClick() {
        //Datos válidos del formulario

        registrarAlumno("", "", "", "", "", "", "", "");
    }

    private void registrarAlumno(String nombre, String apellidoPaterno, String apellidoMaterno, String curp, String matricula,
                                 String telefono, String correoElectronico, String claveAcceso) {
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
                    .registrar(nombre, apellidoPaterno, apellidoMaterno, curp, matricula, telefono, correoElectronico, claveAcceso);

            transaccion.commit();//Guardar cambios en base de datos
            System.out.println("Alumno registrado");
        } catch (CurpDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (MatriculaDuplicadaException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (UsuarioDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }
}
