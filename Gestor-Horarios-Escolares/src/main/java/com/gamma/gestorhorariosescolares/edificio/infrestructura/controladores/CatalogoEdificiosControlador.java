package com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.BuscarEdificios;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificiosData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.GestionarEstatusEdificio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.actualizar.ActualizadorEdficio;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.stages.FormularioEdificioStage;
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

public class CatalogoEdificiosControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<EdificioData> coleccionEdificios;
    private boolean esBusquedaEdificio;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<EdificioData> tablaEdificios;

    public CatalogoEdificiosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaEdificio = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //búsqueda edificios
            tablaEdificios.setDisable(true);
            buscarEdificios(txtBuscar.getText().trim());
            tablaEdificios.setDisable(false);
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaEdificio)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarEdificios();
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
                setGraphic(null);
                if (empty)
                    return;

                EdificioData edificio = getTableView().getItems().get(getIndex());
                Button botonEditor = new Button("Editar");
                botonEditor.setPrefWidth(Double.MAX_VALUE);
                botonEditor.getStyleClass().addAll("b", "btn-success");
                botonEditor.setOnAction(event -> editarEdificio(edificio));
                setGraphic(botonEditor);
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
                setGraphic(null);
                if (empty)
                    return;

                EdificioData edificio = getTableView().getItems().get(getIndex());
                TableRow<EdificioData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEstatus = new Button();
                botonEstatus.setPrefWidth(Double.MAX_VALUE);
                if (edificio.estatus()) {
                    botonEstatus.setText("Deshabilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-danger");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarEdificio(edificio);
                            buscarEdificios();
                        });
                    });
                } else {
                    botonEstatus.setText("Habilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-primary");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarEdificio(edificio);
                            buscarEdificios();
                        });
                    });
                }
                setGraphic(botonEstatus);
            }
        });

        tablaEdificios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaEdificios.getColumns().addAll(columnaClave, columnaNombre, columnaEditar, columnaEstatus);
        tablaEdificios.setItems(coleccionEdificios);
    }

    @FXML
    private void registrarNuevoEdificio() {
        var formulario = new FormularioEdificioStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarEdificios();
    }

    private void editarEdificio(EdificioData edificio) {
        var formulario = new FormularioEdificioStage(edificio);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarEdificios();
    }

    private void habilitarEdificio(EdificioData edificio) {
        cambiarEstatus("habilitar", edificio);
    }

    private void deshabilitarEdificio(EdificioData edificio) {
        cambiarEstatus("deshabilitar", edificio);
    }

    private void cambiarEstatus(String estatus, EdificioData edificio) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var actualizadorEficio = new ActualizadorEdficio(edificioRepositorio);

            GestionarEstatusEdificio gestionarEstatusEdificio = new GestionarEstatusEdificio(buscadorEdificio, actualizadorEficio);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusEdificio.habilitarEdificio(edificio.id());
                case "deshabilitar" -> gestionarEstatusEdificio.deshabilitarEdificio(edificio.id());
            }

            transaccion.commit();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Edificio no encontrado");
            mensaje.showAndWait();
        }
    }

    private void buscarEdificios() {
        esBusquedaEdificio = false;
        txtBuscar.setText("");
        esBusquedaEdificio = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarEdificios(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        EdificiosData edificios;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarEdificios buscarEdificios = new BuscarEdificios(buscadorEdificio);

            if (criterioBusqueda.isBlank()) {
                edificios = buscarEdificios.buscarTodos();
            } else {
                System.out.println("Busqueda de edificios por criterio: '" + criterioBusqueda + "'");
                edificios = buscarEdificios.buscarPorCriterio(criterioBusqueda);
            }

            cargarEdificiosEnTabla(edificios);
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
                mensaje.setTitle("Error de base de datos");
                mensaje.showAndWait();
            });
        }
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
