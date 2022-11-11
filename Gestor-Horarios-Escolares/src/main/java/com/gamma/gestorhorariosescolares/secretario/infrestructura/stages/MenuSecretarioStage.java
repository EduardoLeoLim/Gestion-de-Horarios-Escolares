package com.gamma.gestorhorariosescolares.secretario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores.MenuSecretarioControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MenuSecretarioStage extends CustomStage {

    public MenuSecretarioStage() {
        setTitle("Sistema de Gestión Escolar - Secretario");
        setMinWidth(1100);
        setMinHeight(690);
        setWidth(1100);
        setHeight(690);
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("secretario/infrestructura/vistas/MenuSecretario.fxml"));
            MenuSecretarioControlador menuSecretarioControlador = new MenuSecretarioControlador(this);
            root.setController(menuSecretarioControlador);
            root.load();
            Parent content = root.getRoot();
            setContent(content);
        } catch (IOException e) {
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }

}