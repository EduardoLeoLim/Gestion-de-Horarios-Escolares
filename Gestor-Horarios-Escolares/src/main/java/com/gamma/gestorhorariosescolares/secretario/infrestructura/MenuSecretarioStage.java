package com.gamma.gestorhorariosescolares.secretario.infrestructura;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import javafx.scene.layout.Pane;

public class MenuSecretarioStage extends CustomStage {

    public MenuSecretarioStage() {
        setTitle("Sistema de Gestión Escolar - Secretario");
        setMinWidth(1100);
        setMinHeight(690);
        setWidth(1100);
        setHeight(690);

        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: #EFEFEF; ");
        setContent(panel);
    }
}
