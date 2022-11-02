package com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.infrestructura.stages.MenuAdministradorStage;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.MenuSecretarioStage;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.Autenticacion;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class LoginControlador {
    private final Stage stage;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtClaveAcceso;

    @FXML
    private Button btnIngresar;

    public LoginControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void ingresarClick() {

        String correoElectronico = txtUsuario.getText();
        String claveAcceso = txtClaveAcceso.getText();

        if (hayCamposVacios(correoElectronico, claveAcceso)) {
            Alert mensaje = new Alert(Alert.AlertType.WARNING, "Por favor, ingresa tu usuario y contraseña", ButtonType.OK);
            mensaje.setTitle("Credenciales inválidas");
            mensaje.showAndWait();
            return;
        }

        try {
            UsuarioData usuario = ingresar(correoElectronico, claveAcceso);
            mostrarMenu(usuario);
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Usuario no encontrado");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error Base de datos");
            mensaje.showAndWait();
        }
    }

    private boolean hayCamposVacios(String correoElectronico, String claveAcceso) {
        return (correoElectronico.isBlank() || claveAcceso.isBlank());
    }

    private UsuarioData ingresar(String correoElectronico, String claveAcceso) throws RecursoNoEncontradoException {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            UsuarioRepositorio repositorio = new MySql2oUsuarioRepositorio(transaccion);
            ServicioBuscador<Usuario> buscadorUsuario = new BuscadorUsuario(repositorio);
            Autenticacion autenticacion = new Autenticacion(buscadorUsuario);

            return autenticacion.ingresar(correoElectronico, claveAcceso);
        }
    }

    private void mostrarMenu(UsuarioData usuario) throws RecursoNoEncontradoException {
        switch (usuario.tipo()) {
            case "Administrador":
                new MenuAdministradorStage().show();
                break;
            case "Secretario":
                new MenuSecretarioStage().show();
                break;
            case "Maestro":
                break;
            case "Alumno":
                break;
            default:
                throw new RecursoNoEncontradoException("Tipo de usuario desconocido");
        }

        stage.close();
    }
}