package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnosData;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoAlumnosControlador {
    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    ObservableList<AlumnoData> coleccionAlumnos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<AlumnoData> tablaAlumnos;

    public CatalogoAlumnosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            buscarAlumnos(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
    }

    private void inicializarTabla() {
        coleccionAlumnos = FXCollections.observableArrayList();

        TableColumn<AlumnoData, String> columnaMatricula = new TableColumn<>("Matrícula");
        columnaMatricula.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().matricula()));
        columnaMatricula.setMinWidth(150);

        TableColumn<AlumnoData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<AlumnoData, String> columnaApellidoPaterno = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoPaterno()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<AlumnoData, String> columnaApellidoMaterno = new TableColumn<>("Apellido Materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoMaterno()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<AlumnoData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<AlumnoData, String> columnaEditar = new TableColumn<>("");
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
                    AlumnoData alumno = getTableView().getItems().get(getIndex());
                    Button botonEditar = new Button("Editar");
                    botonEditar.setPrefWidth(Double.MAX_VALUE);
                    botonEditar.getStyleClass().addAll("b", "btn-success");
                    botonEditar.setOnAction(event -> editarAlumno(alumno));
                    setGraphic(botonEditar);
                }
            }
        });

        TableColumn<AlumnoData, String> columnaEstatus = new TableColumn<>("");
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
                AlumnoData alumno = getTableView().getItems().get(getIndex());
                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (alumno.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> deshabilitarAlumno(alumno));
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> habilitarAlumno(alumno));
                }
                setGraphic(botonEliminar);
            }
        });

        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaAlumnos.getColumns().addAll(columnaMatricula, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno,
                columnaEditar, columnaEstatus);
        tablaAlumnos.setItems(coleccionAlumnos);
    }

    private void editarAlumno(AlumnoData alumno) {

    }

    private void habilitarAlumno(AlumnoData alumno) {

    }

    private void deshabilitarAlumno(AlumnoData alumno) {

    }

    private void buscarAlumnos(String criterioBusqueda) {
        System.out.println("Busqueda de alumnos por criterio: '" + criterioBusqueda + "'");
    }

    private void cargarAlumnosEnTabla(AlumnosData listaAlumnos) {
        if (listaAlumnos == null)
            return;

        coleccionAlumnos.clear();
        coleccionAlumnos.addAll(listaAlumnos.alumnos());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
