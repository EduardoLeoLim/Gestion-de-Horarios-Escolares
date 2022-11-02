package com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.ActualizarEdificio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.RegistrarEdificio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.actualizar.ActualizadorEdficio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.registrar.RegistradorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioEdificioControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final EdificioData edificio;

    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;

    public FormularioEdificioControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.edificio = null;
    }

    public FormularioEdificioControlador(Stage stage, EdificioData edificio) {
        if (stage == null || edificio == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.edificio = edificio;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosEdificio();
    }

    private void cargarDatosEdificio() {
        if (edificio == null)
            throw new NullPointerException();
        txtClave.setText(edificio.clave());
        txtNombre.setText(edificio.nombre());
    }

    @FXML
    private void guardarEdificio() {
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();

        if (!sonValidosDatosFormulario(clave, nombre))
            return;

        if (esNuevoRegistro)
            registrarEdificio(clave, nombre);
        else
            actualizar(clave, nombre);
    }

    private Boolean sonValidosDatosFormulario(String clave, String nombre) {
        if (clave.isBlank() || nombre.isBlank()) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacios en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacios");
            mensaje.showAndWait();
            return false;
        }

        return true;
    }

    private void registrarEdificio(String clave, String nombre) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var registradorEdificio = new RegistradorEdificio(edificioRepositorio);

            RegistrarEdificio registrarEdificio = new RegistrarEdificio(buscadorEdificio, registradorEdificio);
            registrarEdificio.registrar(clave, nombre);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Edificio registrado correctamente", ButtonType.OK);
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

    private void actualizar(String clave, String nombre) {
        if (edificio == null)
            throw new NullPointerException();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var actualizadorEdificio = new ActualizadorEdficio(edificioRepositorio);

            ActualizarEdificio actualizarEdificio = new ActualizarEdificio(buscadorEdificio, actualizadorEdificio);

            EdificioData edificioData = new EdificioData(this.edificio.id(), clave, nombre, this.edificio.estatus());

            actualizarEdificio.actualizar(edificioData);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Edificio actualizado correctamente", ButtonType.OK);
            mensaje.setTitle("Actualización exitosa");
            mensaje.showAndWait();

            cerrarFormulario();
        } catch (ClaveDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Clave duplicada");
            mensaje.showAndWait();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Recurso no encontrado");
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
