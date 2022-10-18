package com.gamma.gestorhorariosescolares;

import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        new LoginStage().show();
    }
}