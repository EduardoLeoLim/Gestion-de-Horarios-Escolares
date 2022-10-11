package com.gamma.gestorhorariosescolares.usuario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores.LoginControlador;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginStage extends CustomStage {
    public LoginStage() {
        setTitle("Iniciar sesión ");
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("usuario/infrestructura/vistas/Login.fxml"));
            LoginControlador loginController = new LoginControlador();
            root.setController(loginController);
            root.load();
            setContent(root.getRoot());
        } catch (IOException e) {
            System.err.println("No se encuentra el recurso views/user/Login.fxml");
        }
    }
}
