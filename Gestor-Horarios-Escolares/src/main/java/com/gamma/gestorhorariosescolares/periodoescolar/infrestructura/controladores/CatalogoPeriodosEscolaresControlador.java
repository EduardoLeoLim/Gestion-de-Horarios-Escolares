package com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.GestionarEstatusPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.actualizar.ActualizadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.stages.FormularioPeriodoEscolarStage;
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

public class CatalogoPeriodosEscolaresControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<PeriodoEscolarData> coleccionPeriodosEscolares;
    private boolean esBusquedaPeriodoEscolar;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<PeriodoEscolarData> tablaPeriodosEscolares;

    public CatalogoPeriodosEscolaresControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaPeriodoEscolar = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Busca periodos escolares con hilo seguro
            tablaPeriodosEscolares.setDisable(true);
            buscarPeriodosEscolares(txtBuscar.getText().trim());
            tablaPeriodosEscolares.setDisable(false);
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaPeriodoEscolar)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarPeriodosEscolares();
    }

    private void inicializarTabla() {
        coleccionPeriodosEscolares = FXCollections.observableArrayList();

        TableColumn<PeriodoEscolarData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<PeriodoEscolarData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<PeriodoEscolarData, String> columnaFechaInicio = new TableColumn<>("Fecha de inicio");
        columnaFechaInicio.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().fechaInicioFormateada()));
        columnaFechaInicio.setMinWidth(150);

        TableColumn<PeriodoEscolarData, String> columnaFechaFin = new TableColumn<>("Fecha de inicio");
        columnaFechaFin.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().fechaFinFormateada()));
        columnaFechaFin.setMinWidth(150);

        TableColumn<PeriodoEscolarData, String> columnaEditar = new TableColumn<>();
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

                PeriodoEscolarData periodoEscolar = getTableView().getItems().get(getIndex());
                Button botonEditor = new Button("Editar");
                botonEditor.setPrefWidth(Double.MAX_VALUE);
                botonEditor.getStyleClass().addAll("b", "btn-success");
                botonEditor.setOnAction(event -> editarPeriodoEscolar(periodoEscolar));
                setGraphic(botonEditor);
            }
        });

        TableColumn<PeriodoEscolarData, String> columnaEstatus = new TableColumn<>("");
        columnaEstatus.setMinWidth(120);
        columnaEstatus.setMaxWidth(120);
        columnaEstatus.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText("");
                setGraphic(null);
                if (empty)
                    return;

                PeriodoEscolarData periodoEscolar = getTableView().getItems().get(getIndex());
                TableRow<PeriodoEscolarData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEstatus = new Button();
                botonEstatus.setPrefWidth(Double.MAX_VALUE);
                if (periodoEscolar.estatus()) {
                    botonEstatus.setText("Deshabilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-danger");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarPeriodoEscolar(periodoEscolar);
                            buscarPeriodosEscolares();
                        });
                    });
                } else {
                    botonEstatus.setText("Habilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-primary");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarPeriodoEscolar(periodoEscolar);
                            buscarPeriodosEscolares();
                        });
                    });
                }
                setGraphic(botonEstatus);

            }
        });

        tablaPeriodosEscolares.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaPeriodosEscolares.getColumns().addAll(columnaClave, columnaNombre, columnaFechaInicio, columnaFechaFin,
                columnaEditar, columnaEstatus);
        tablaPeriodosEscolares.setItems(coleccionPeriodosEscolares);
    }

    @FXML
    private void registrarNuevoPeriodoEscolar() {
        var formulario = new FormularioPeriodoEscolarStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarPeriodosEscolares();
    }

    private void editarPeriodoEscolar(PeriodoEscolarData periodoEscolar) {
        var formulario = new FormularioPeriodoEscolarStage(periodoEscolar);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarPeriodosEscolares();
    }

    private void habilitarPeriodoEscolar(PeriodoEscolarData periodoEscolar) {
        cambiarEstatus("habilitar", periodoEscolar);
    }

    private void deshabilitarPeriodoEscolar(PeriodoEscolarData periodoEscolar) {
        cambiarEstatus("deshabilitar", periodoEscolar);
    }

    private void cambiarEstatus(String estatus, PeriodoEscolarData periodoEscolar) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);
            var actualizadorPeriodoEscolar = new ActualizadorPeriodoEscolar(periodoEscolarRepositorio);

            GestionarEstatusPeriodoEscolar gestionarEstatusPeriodoEscolar = new GestionarEstatusPeriodoEscolar(buscadorPeriodoEscolar, actualizadorPeriodoEscolar);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusPeriodoEscolar.habilitarPeriodoEscolar(periodoEscolar.id());
                case "deshabilitar" -> gestionarEstatusPeriodoEscolar.deshabilitarPeriodoEscolar(periodoEscolar.id());
            }

            transaccion.commit();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Periodo escolar no encontrado");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        }
    }

    private void buscarPeriodosEscolares() {
        esBusquedaPeriodoEscolar = false;
        txtBuscar.setText("");
        esBusquedaPeriodoEscolar = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarPeriodosEscolares(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        PeriodosEscolaresData periodosEscolares;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);

            if (criterioBusqueda.isBlank()) {
                periodosEscolares = buscarPeriodosEscolares.buscarTodos();
            } else {
                periodosEscolares = buscarPeriodosEscolares.buscarPorCriterio(criterioBusqueda);
            }

            cargarPeriodosEscolaresEnTabla(periodosEscolares);
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
                mensaje.setTitle("Error de base de datos");
                mensaje.showAndWait();
            });
        }
    }

    private void cargarPeriodosEscolaresEnTabla(PeriodosEscolaresData listaPeriodosEscolares) {
        if (listaPeriodosEscolares == null)
            return;

        coleccionPeriodosEscolares.clear();
        coleccionPeriodosEscolares.addAll(listaPeriodosEscolares.periodosEscolares());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}