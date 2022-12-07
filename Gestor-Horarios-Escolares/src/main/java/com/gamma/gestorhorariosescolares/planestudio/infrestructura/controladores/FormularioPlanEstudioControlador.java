package com.gamma.gestorhorariosescolares.planestudio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.RegistrarPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.buscar.BuscadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.registrar.RegistradorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia.MySql2oPlanEstudioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioPlanEstudioControlador {

    private final Stage stage;

    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;

    public FormularioPlanEstudioControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void guardarPlanEstudio() {
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();

        if (!sonValidosDatosFormuario(clave, nombre))
            return;

        registrarPlanEstudio(clave, nombre);
    }

    private Boolean sonValidosDatosFormuario(String clave, String nombre) {
        if (clave.isBlank() || nombre.isBlank()) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacios en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacios");
            mensaje.showAndWait();
            return false;
        }

        return true;
    }

    private void registrarPlanEstudio(String clave, String nombre) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var planEstudioRepositorio = new MySql2oPlanEstudioRepositorio(transaccion);

            //Servicios
            var buscadorPlanEstudio = new BuscadorPlanEstudio(planEstudioRepositorio);
            var registradorPlanEstudio = new RegistradorPlanEstudio(planEstudioRepositorio);

            RegistrarPlanEstudio registrarPlanEstudio = new RegistrarPlanEstudio(buscadorPlanEstudio, registradorPlanEstudio);
            registrarPlanEstudio.registrar(clave, nombre);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Plan de Estudio registrado correctamente", ButtonType.OK);
            mensaje.setTitle("Registro exitoso");
            mensaje.showAndWait();

            cerrarFormulario();
        } catch (ClaveDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Clave duplicada");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
            cerrarFormulario();
        }

    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }
}
