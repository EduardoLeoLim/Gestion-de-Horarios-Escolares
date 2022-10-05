package com.gamma.gestorhorariosescolares.infrestructura.usuario.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.infrestructura.compartido.stage.CustomStage;
import com.gamma.gestorhorariosescolares.infrestructura.usuario.controladores.LoginControlador;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginStage extends CustomStage {
    public LoginStage() {
        setTitle("Iniciar sesión ");
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("infrestructura/usuario/vistas/Login.fxml"));
            LoginControlador loginController = new LoginControlador();
            root.setController(loginController);
            root.load();
            setContent(root.getRoot());
        } catch (IOException e) {
            System.err.println("No se encuentra el recurso views/user/Login.fxml");
        }
    }
}
