package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestrosData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoMaestrosControlador {
    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    private ObservableList<MaestroData> coleccionMaestros;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<MaestroData> tablaMaestros;

    public CatalogoMaestrosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            buscarMaestros(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        //Buscar todos los maestros
    }

    private void inicializarTabla() {
        coleccionMaestros = FXCollections.observableArrayList();

        TableColumn<MaestroData, String> columnaNoPersonal = new TableColumn<>("No. Personal");
        columnaNoPersonal.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().noPersonal()));
        columnaNoPersonal.setMinWidth(150);

        TableColumn<MaestroData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<MaestroData, String> columnaApellidoPaterno = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoPaterno()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<MaestroData, String> columnaApellidoMaterno = new TableColumn<>("Apellido Materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoMaterno()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<MaestroData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<MaestroData, String> columnaEditar = new TableColumn<>("");
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
                    MaestroData maestro = getTableView().getItems().get(getIndex());
                    Button botonEditar = new Button("Editar");
                    botonEditar.setPrefWidth(Double.MAX_VALUE);
                    botonEditar.getStyleClass().addAll("b", "btn-success");
                    botonEditar.setOnAction(event -> editarMaestro(maestro));
                    setGraphic(botonEditar);
                }
            }
        });

        TableColumn<MaestroData, String> columnaEstatus = new TableColumn<>("");
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
                MaestroData maestro = getTableView().getItems().get(getIndex());
                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (maestro.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> deshabilitarMaestro(maestro));
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> habilitarMaestro(maestro));
                }
                setGraphic(botonEliminar);
            }
        });

        tablaMaestros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMaestros.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno, columnaCorreoElectronico, columnaEditar, columnaEstatus);
        tablaMaestros.setItems(coleccionMaestros);
    }

    private void editarMaestro(MaestroData maestro) {

    }

    private void habilitarMaestro(MaestroData maestro) {

    }

    private void deshabilitarMaestro(MaestroData maestro) {

    }

    private void buscarMaestros(String criterioBusquesa) {

    }

    private void cargarMaestrosEnTabla(MaestrosData listaMaestros) {
        if (listaMaestros == null)
            return;

        coleccionMaestros.clear();
        coleccionMaestros.addAll(listaMaestros.maestros());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
