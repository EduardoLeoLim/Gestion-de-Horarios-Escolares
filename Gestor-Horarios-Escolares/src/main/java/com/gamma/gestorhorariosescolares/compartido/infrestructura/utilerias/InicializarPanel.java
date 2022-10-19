package com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias;

import com.gamma.gestorhorariosescolares.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InicializarPanel {
    public static AnchorPane inicializarAnchorPane(String pathRecurso, Object controlador) throws IOException {
        FXMLLoader root = new FXMLLoader(App.class.getResource(pathRecurso));
        if (controlador != null)
            root.setController(controlador);
        try {
            return root.load();
        } catch (IOException e) {
            throw e;
        }
    }
}
