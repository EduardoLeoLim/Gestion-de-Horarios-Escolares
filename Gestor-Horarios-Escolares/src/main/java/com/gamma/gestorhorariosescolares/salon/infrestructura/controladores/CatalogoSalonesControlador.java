package com.gamma.gestorhorariosescolares.salon.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonesData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoSalonesControlador {
    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    private ObservableList<SalonData> coleccionSalones;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<SalonData> tablaSalones;

    public CatalogoSalonesControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //busca salones
            buscarSalones(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
    }

    private void inicializarTabla() {
        coleccionSalones = FXCollections.observableArrayList();

        TableColumn<SalonData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<SalonData, String> columnaEdificio = new TableColumn<>("Edificio");
        columnaEdificio.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().edificio().nombre()));
        columnaEdificio.setMinWidth(150);

        TableColumn<SalonData, String> columnaCapacidad = new TableColumn<>("Capacidad");
        columnaCapacidad.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().capacidad() + " alumnos"));
        columnaCapacidad.setMinWidth(150);

        TableColumn<SalonData, String> columnaEditar = new TableColumn<>();
        columnaEditar.setMinWidth(80);
        columnaEditar.setMaxWidth(80);
        columnaEditar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty)
                    return;

                SalonData salon = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-success");
                botonEditar.setOnAction(event -> editarSalon(salon));
            }
        });

        TableColumn<SalonData, String> columnaEstatus = new TableColumn<>();
        columnaEstatus.setMinWidth(120);
        columnaEstatus.setMaxWidth(120);
        columnaEstatus.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty)
                    return;

                SalonData salon = getTableView().getItems().get(getIndex());
                Button botonEstatus = new Button(salon.estatus() ? "Deshabilitar" : "Habilitar");
                botonEstatus.getStyleClass().addAll("b", salon.estatus() ? "btn-danger" : "btn-primary");
                botonEstatus.setOnAction(event -> {
                    if (salon.estatus())
                        deshabilitarSalon(salon);
                    else
                        habilitarSalon(salon);
                });
                setGraphic(botonEstatus);
            }
        });

        tablaSalones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaSalones.getColumns().addAll(columnaClave, columnaEdificio, columnaCapacidad, columnaEditar, columnaEstatus);
        tablaSalones.setItems(coleccionSalones);
    }

    private void editarSalon(SalonData salon) {

    }

    private void habilitarSalon(SalonData salon) {

    }

    private void deshabilitarSalon(SalonData salon) {

    }

    private void buscarSalones(String criterioBusqueda) {
        System.out.println("Busqueda de salones por criterio: '" + criterioBusqueda + "'");
    }

    private void cargarSalonesEnTabla(SalonesData listaSalones) {
        if (listaSalones == null)
            return;

        coleccionSalones.clear();
        coleccionSalones.addAll(listaSalones.salones());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
