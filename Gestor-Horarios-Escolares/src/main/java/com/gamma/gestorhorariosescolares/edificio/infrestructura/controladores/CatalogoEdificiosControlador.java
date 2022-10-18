package com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificiosData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoEdificiosControlador {
    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    private ObservableList<EdificioData> coleccionEdificios;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<EdificioData> tablaEdificios;

    public CatalogoEdificiosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //busca edificios
            buscarEdificios(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
    }

    private void inicializarTabla() {
        coleccionEdificios = FXCollections.observableArrayList();

        TableColumn<EdificioData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<EdificioData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<EdificioData, String> columnaEditar = new TableColumn<>();
        columnaEditar.setMinWidth(80);
        columnaEditar.setMaxWidth(80);
        columnaEditar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                if (empty) {
                    setGraphic(null);
                } else {
                    EdificioData edificio = getTableView().getItems().get(getIndex());
                    Button botonEditor = new Button("Editar");
                    botonEditor.setPrefWidth(Double.MAX_VALUE);
                    botonEditor.getStyleClass().addAll("b", "btn-success");
                    botonEditor.setOnAction(event -> editarEdificio(edificio));
                }
            }
        });

        TableColumn<EdificioData, String> columnaEstatus = new TableColumn<>("");
        columnaEstatus.setMinWidth(120);
        columnaEstatus.setMaxWidth(120);
        columnaEstatus.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                EdificioData edificio = getTableView().getItems().get(getIndex());
                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (edificio.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> deshabilitarEdificio(edificio));
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> habilitarEdificio(edificio));
                }
                setGraphic(botonEliminar);
            }
        });

        tablaEdificios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaEdificios.getColumns().addAll(columnaClave, columnaNombre, columnaEditar, columnaEstatus);
        tablaEdificios.setItems(coleccionEdificios);
    }

    private void editarEdificio(EdificioData edificio) {

    }

    private void habilitarEdificio(EdificioData edificio) {

    }

    private void deshabilitarEdificio(EdificioData edificio) {

    }

    private void buscarEdificios(String criterioBusqueda) {
        System.out.println("Busqueda de edificios por criterio: '" + criterioBusqueda + "'");
    }

    private void cargarEdificiosEnTabla(EdificiosData listaEdificios) {
        if (listaEdificios == null)
            return;

        coleccionEdificios.clear();
        coleccionEdificios.addAll(listaEdificios.edificios());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
