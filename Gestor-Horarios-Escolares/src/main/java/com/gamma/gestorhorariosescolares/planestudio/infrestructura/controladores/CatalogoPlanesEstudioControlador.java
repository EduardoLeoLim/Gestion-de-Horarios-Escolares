package com.gamma.gestorhorariosescolares.planestudio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanesEstudioData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CatalogoPlanesEstudioControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<PlanEstudioData> coleccionPlanesEstudio;
    private boolean esBusquedaPlanEstudio;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<PlanEstudioData> tablaPlanesEstudio;

    public CatalogoPlanesEstudioControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaPlanEstudio = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Búsqueda de planes de estudio
            Platform.runLater(() -> {
                tablaPlanesEstudio.setDisable(true);
                buscarPlanesEstudio(txtBuscar.getText().trim());
                tablaPlanesEstudio.setDisable(false);
            });
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaPlanEstudio)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarPlanesEstudio();
    }

    @FXML
    private void registrarNuevoPlanEstudio() {

    }

    private void editarPlanEstudio(PlanesEstudioData planesEstudio) {

    }

    private void inicializarTabla() {
        coleccionPlanesEstudio = FXCollections.observableArrayList();
    }

    private void habilitarPlanEstudio(PlanesEstudioData planesEstudio) {
        cambiarEstatus("habilitar", planesEstudio);
    }

    private void deshabilitarPlanEstudio(PlanesEstudioData planesEstudio) {
        cambiarEstatus("deshabilitar", planesEstudio);
    }

    private void cambiarEstatus(String estatus, PlanesEstudioData planEstudio) {

    }

    private void buscarPlanesEstudio() {
        esBusquedaPlanEstudio = false;
        txtBuscar.setText("");
        esBusquedaPlanEstudio = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarPlanesEstudio(String criterioBusqueda) {

    }

    private void cargarPlanesEstudioEnTabla(PlanesEstudioData listaPlanesEstudio) {
        if (listaPlanesEstudio == null)
            return;

        coleccionPlanesEstudio.clear();
        coleccionPlanesEstudio.addAll(listaPlanesEstudio.planesEstudio());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}