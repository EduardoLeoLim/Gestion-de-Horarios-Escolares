package com.gamma.gestorhorariosescolares.materia.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriasData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoMateriasControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<MateriaData> coleccionMaterias;
    private boolean esBusquedaMateria;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<MateriaData> tablaMaterias;

    public CatalogoMateriasControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {

        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Búsqueda de materias
            Platform.runLater(() -> {
                tablaMaterias.setDisable(true);
                buscarMaterias(txtBuscar.getText().trim());
                tablaMaterias.setDisable(false);
            });
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))
                return;
            temporizadorBusqueda.reiniciar();
        });

        //Configuracion de tabla
        inicializarTabla();
    }

    private void inicializarTabla() {
        coleccionMaterias = FXCollections.observableArrayList();

        TableColumn<MateriaData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<MateriaData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<MateriaData, String> columnaHorasPracticas = new TableColumn<>("Horas prácticas");
        columnaHorasPracticas.setCellValueFactory(ft -> new SimpleStringProperty("" + ft.getValue().horasPracticas()));
        columnaHorasPracticas.setMinWidth(150);

        TableColumn<MateriaData, String> columnaHorasTeoricas = new TableColumn<>("Horas teóricas");
        columnaHorasTeoricas.setCellValueFactory(ft -> new SimpleStringProperty("" + ft.getValue().horasTeoricas()));
        columnaHorasTeoricas.setMinWidth(150);

        TableColumn<MateriaData, String> columnaEditar = new TableColumn<>();
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

                MateriaData materia = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-success");
                botonEditar.setOnAction(event -> editarMateria(materia));
                setGraphic(botonEditar);
            }
        });

        TableColumn<MateriaData, String> columnaEstatus = new TableColumn<>();
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

                MateriaData materia = getTableView().getItems().get(getIndex());
                TableRow<MateriaData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEstatus = new Button();
                if (materia.estatus()) {
                    botonEstatus.setText("Deshabilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-danger");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarMateria(materia);
                            buscarMaterias();
                        });
                    });
                } else {
                    botonEstatus.setText("Habilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-primary");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarMateria(materia);
                            buscarMaterias();
                        });
                    });
                }
                setGraphic(botonEstatus);
            }
        });

        tablaMaterias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMaterias.getColumns().addAll(columnaClave, columnaNombre, columnaHorasPracticas, columnaHorasTeoricas,
                columnaEditar, columnaEstatus);
        tablaMaterias.setItems(coleccionMaterias);
    }

    private void editarMateria(MateriaData materia) {

    }

    private void habilitarMateria(MateriaData materia) {

    }

    private void deshabilitarMateria(MateriaData materia) {

    }

    private void buscarMaterias() {
        esBusquedaMateria = false;
        txtBuscar.setText("");
        temporizadorBusqueda.reiniciar();
        esBusquedaMateria = true;
    }

    private void buscarMaterias(String criterioBusqueda) {
        System.out.println("Busqueda de materias por criterio: '" + criterioBusqueda + "'");
    }

    private void cargarMateriaEnTabla(MateriasData listaMaterias) {
        if (listaMaterias == null)
            return;

        coleccionMaterias.clear();
        coleccionMaterias.addAll(listaMaterias.materias());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
