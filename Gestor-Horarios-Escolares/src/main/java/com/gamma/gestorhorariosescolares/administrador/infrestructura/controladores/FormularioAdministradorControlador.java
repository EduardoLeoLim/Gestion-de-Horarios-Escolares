package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioAdministradorControlador {

    private final Stage stage;

    private boolean esNuevoRegistro;

    private AdministradorData administrador;

    @FXML
    private TextField txtNoPersonal;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtApellidoMaterno;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreoElectronico;

    @FXML
    private TextField txtClaveAcceso;

    @FXML
    private TextField txtConfirmarClaveAcceso;

    public FormularioAdministradorControlador(Stage stage) {
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.administrador = null;
    }

    public FormularioAdministradorControlador(Stage stage, AdministradorData administrador) {
        if (administrador == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.administrador = administrador;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosAdministrador();
    }

    @FXML
    private void cerrarFormulario(){
        stage.close();
    }

    private void cargarDatosAdministrador(){
        txtNoPersonal.setText(administrador.noPersonal());
        txtNombre.setText(administrador.nombre());
        txtApellidoPaterno.setText(administrador.apellidoPaterno());
        txtApellidoMaterno.setText(administrador.apellidoMaterno());
        txtTelefono.setText(administrador.usuario().telefono());
        txtCorreoElectronico.setText(administrador.usuario().correoElectronico());
        txtClaveAcceso.setText(administrador.usuario().claveAcceso());
        txtConfirmarClaveAcceso.setText(administrador.usuario().claveAcceso());
    }
}
