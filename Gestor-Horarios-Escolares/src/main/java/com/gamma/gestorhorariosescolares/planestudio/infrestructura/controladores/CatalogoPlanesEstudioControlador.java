package com.gamma.gestorhorariosescolares.planestudio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.BuscarPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanesEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.buscar.BuscadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia.MySql2oPlanEstudioRepositorio;
import com.gamma.gestorhorariosescolares.planestudio.infrestructura.stages.FormularioPlanEstudioStage;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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
        var formulario = new FormularioPlanEstudioStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarPlanesEstudio();

    }

    private void editarPlanEstudio(PlanesEstudioData planesEstudio) {

    }

    private void inicializarTabla() {
        coleccionPlanesEstudio = FXCollections.observableArrayList();
        TableColumn<PlanEstudioData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft-> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);


        tablaPlanesEstudio.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaPlanesEstudio.getColumns().addAll(columnaClave);
        tablaPlanesEstudio.setItems(coleccionPlanesEstudio);
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
        PlanesEstudioData planesEstudio;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()){
            //Repositoro
            var planEstudioRepositorio = new MySql2oPlanEstudioRepositorio(transaccion);

            //Servicios
            var buscadorPlanEstudio = new BuscadorPlanEstudio(planEstudioRepositorio);
            BuscarPlanEstudio buscarPlanEstudio = new BuscarPlanEstudio(buscadorPlanEstudio);

            if(criterioBusqueda.isBlank()){
                planesEstudio = buscarPlanEstudio.buscarTodos();
            }else{
                planesEstudio = buscarPlanEstudio.buscarPorCriterio(criterioBusqueda);
            }
            cargarPlanesEstudioEnTabla(planesEstudio);

        }catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        }
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