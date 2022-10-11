package com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginControlador {
    @FXML
    private Label welcomeText;

    public LoginControlador() {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}