package com.gamma.gestorhorariosescolares.infrastructure.user.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.infrastructure.share.stage.CustomStage;
import com.gamma.gestorhorariosescolares.infrastructure.user.viewcontrollers.LoginController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginStage extends CustomStage {
    public LoginStage() {
        setTitle("Iniciar sesión ");
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("infrastructure/user/views/Login.fxml"));
            root.setController(new LoginController());
            root.load();
            setContent(root.getRoot());
        } catch (IOException e) {
            System.err.println("No se encuentra el recurso views/user/Login.fxml");
        }
    }
}
