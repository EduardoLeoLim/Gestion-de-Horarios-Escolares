package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.App;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MenuAdministradorControlador {
    @FXML
    private VBox vbMenu;

    public MenuAdministradorControlador(){

    }

    @FXML
    public void initialize() {
        //Solo se puede acceder a los recursos @FXML aquí
        vbMenu.getStylesheets().add(App.class.getResource("compartido/infrestructura/estilos/Menu.css").toExternalForm());
    }
}
