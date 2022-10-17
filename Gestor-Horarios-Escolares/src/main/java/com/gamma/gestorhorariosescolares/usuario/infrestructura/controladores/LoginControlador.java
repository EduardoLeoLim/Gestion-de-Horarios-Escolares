package com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.MenuAdministradorStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        var menuAdministrador = new MenuAdministradorStage();
        menuAdministrador.show();
        stage.close();
    }
}