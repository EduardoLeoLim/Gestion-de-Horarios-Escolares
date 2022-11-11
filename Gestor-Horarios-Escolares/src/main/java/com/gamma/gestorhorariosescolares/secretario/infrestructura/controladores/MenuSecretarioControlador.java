package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuSecretarioControlador {

    private final Stage stage;

    @FXML
    private BorderPane panelMenuSecretario;
    @FXML
    private VBox vbMenu;
    @FXML
    private Button btnCatalogoGrupos;
    @FXML
    private Button btnCatalogoInscripciones;
    @FXML
    private Button btnConsultarHorarios;
    @FXML
    private Button btnRegistrarClase;
    @FXML
    private Button btnCerrarSesion;

    public MenuSecretarioControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Solo se puede acceder a los recursos @FXML aquí
        mostrarCatalogoGruposClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarCatalogoGruposClick() {

    }

    @FXML
    protected void mostrarCatalogoInscripcionesClick() {

    }

    @FXML
    protected void mostrarConsultarHorariosClick() {

    }

    @FXML
    protected void mostrarRegistrarClaseClick() {

    }

    @FXML
    protected void cerrarSesionClick() {
        new LoginStage().show();
        stage.close();
    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos");
    }

}