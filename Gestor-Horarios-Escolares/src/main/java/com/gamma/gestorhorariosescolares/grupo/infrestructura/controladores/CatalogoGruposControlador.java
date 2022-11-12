package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CatalogoGruposControlador {

    private final Stage stage;

    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;

    public CatalogoGruposControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void registrarNuevoGrupo() {

    }
}
