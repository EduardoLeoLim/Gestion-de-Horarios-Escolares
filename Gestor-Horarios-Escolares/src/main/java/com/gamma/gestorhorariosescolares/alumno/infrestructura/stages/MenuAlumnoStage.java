package com.gamma.gestorhorariosescolares.alumno.infrestructura.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores.MenuAdministradorControlador;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores.MenuAlumnoControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MenuAlumnoStage extends CustomStage {

    public MenuAlumnoStage(){
        setTitle("Sistema de Gestión Escolar - Alumno");
        setMinWidth(1100);
        setMinHeight(690);
        setWidth(1100);
        setHeight(690);
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("administrador/infrestructura/vistas/MenuAlumno.fxml"));
            MenuAlumnoControlador menuAlumnoControlador = new MenuAlumnoControlador(this);
            root.setController(menuAlumnoControlador);
            root.load();
            Parent content = root.getRoot();
            setContent(content);
        } catch (IOException e) {
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }
}
