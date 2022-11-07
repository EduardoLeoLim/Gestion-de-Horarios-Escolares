package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnosData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.BuscarAlumnos;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.GestionarEstatusAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar.ActualizadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.stages.FormularioAlumnoStage;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
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

public class CatalogoAlumnosControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<AlumnoData> coleccionAlumnos;
    private boolean esBusquedaAlumno;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<AlumnoData> tablaAlumnos;

    public CatalogoAlumnosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaAlumno = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Búsqueda de alumnos
            Platform.runLater(() -> {
                tablaAlumnos.setDisable(true);
                buscarAlumnos(txtBuscar.getText().trim());
                tablaAlumnos.setDisable(false);
            });
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaAlumno)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarAlumnos();
    }

    private void inicializarTabla() {
        coleccionAlumnos = FXCollections.observableArrayList();

        TableColumn<AlumnoData, String> columnaMatricula = new TableColumn<>("Matrícula");
        columnaMatricula.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().matricula()));
        columnaMatricula.setMinWidth(150);

        TableColumn<AlumnoData, String> columnaCurp = new TableColumn<>("CURP");
        columnaCurp.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().curp()));
        columnaCurp.setMinWidth(150);

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
                setGraphic(null);
                if (empty)
                    return;

                AlumnoData alumno = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-success");
                botonEditar.setOnAction(event -> editarAlumno(alumno));
                setGraphic(botonEditar);
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
                setGraphic(null);
                if (empty)
                    return;

                AlumnoData alumno = getTableView().getItems().get(getIndex());
                TableRow<AlumnoData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEstatus = new Button();
                botonEstatus.setPrefWidth(Double.MAX_VALUE);
                if (alumno.estatus()) {
                    botonEstatus.setText("Deshabilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-danger");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarAlumno(alumno);
                            buscarAlumnos();
                        });
                    });
                } else {
                    botonEstatus.setText("Habilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-primary");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarAlumno(alumno);
                            buscarAlumnos();
                        });
                    });
                }
                setGraphic(botonEstatus);
            }
        });

        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaAlumnos.getColumns().addAll(columnaMatricula, columnaCurp, columnaNombre, columnaApellidoPaterno,
                columnaApellidoMaterno, columnaCorreoElectronico, columnaEditar, columnaEstatus);
        tablaAlumnos.setItems(coleccionAlumnos);
    }

    @FXML
    private void registrarNuevoAlumno() {
        var formulario = new FormularioAlumnoStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarAlumnos();
    }

    private void editarAlumno(AlumnoData alumno) {
        var formulario = new FormularioAlumnoStage(alumno);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarAlumnos();
    }

    private void habilitarAlumno(AlumnoData alumno) {
        cambiarEstatus("habilitar", alumno);
    }

    private void deshabilitarAlumno(AlumnoData alumno) {
        cambiarEstatus("deshabilitar", alumno);
    }

    private void cambiarEstatus(String estatus, AlumnoData alumno) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(transaccion);

            //Servicios
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var actualizadorAlumno = new ActualizadorAlumno(alumnoRepositorio);

            GestionarEstatusAlumno gestionarEstatusAlumno = new GestionarEstatusAlumno(buscadorAlumno, actualizadorAlumno);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusAlumno.habilitarAlumno(alumno.id());
                case "deshabilitar" -> gestionarEstatusAlumno.deshabilitarAlumno(alumno.id());
            }

            transaccion.commit();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    public void buscarAlumnos() {
        esBusquedaAlumno = false;
        txtBuscar.setText("");
        esBusquedaAlumno = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarAlumnos(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        AlumnosData alumnos;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection trasaccion = conexion.beginTransaction()) {
            //Repositorios
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(trasaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(trasaccion);

            //Servicios
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            BuscarAlumnos buscarAlumnos = new BuscarAlumnos(buscadorAlumno, buscadorUsuario);

            if (criterioBusqueda.isBlank()) {
                alumnos = buscarAlumnos.buscarTodos();
            } else {
                System.out.println("Busqueda de alumnos por criterio: '" + criterioBusqueda + "'");
                alumnos = buscarAlumnos.buscarPorCriterio(criterioBusqueda);
            }

            cargarAlumnosEnTabla(alumnos);
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        }
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
