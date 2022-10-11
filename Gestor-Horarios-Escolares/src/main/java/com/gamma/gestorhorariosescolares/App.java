package com.gamma.gestorhorariosescolares;

import com.gamma.gestorhorariosescolares.usuario.controladores.infrestructura.stages.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}