package com.gamma.gestorhorariosescolares.usuario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores.MenuAdministradorControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MenuAdministradorStage extends CustomStage {

    public MenuAdministradorStage(){
        setTitle("Sistema de Gestión Escolar - Administrador");
        setMinWidth(1100);
        setMinHeight(690);
        setWidth(1100);
        setHeight(690);
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("administrador/infrestructura/vistas/MenuAdministrador.fxml"));
            MenuAdministradorControlador menuAdministradorControlador = new MenuAdministradorControlador(this);
            root.setController(menuAdministradorControlador);
            root.load();
            Parent content = root.getRoot();
            setContent(content);
        }catch (IOException e){
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }
}
