package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.BuscarMaestros;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.GestionarEstatusMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestrosData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.actualizar.ActualizadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.stages.FormularioMaestroStage;
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

public class CatalogoMaestrosControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<MaestroData> coleccionMaestros;
    private Boolean esBusquedaDeMaestro;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<MaestroData> tablaMaestros;

    public CatalogoMaestrosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        esBusquedaDeMaestro = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //Búsqueda de maestros
            tablaMaestros.setDisable(true);
            buscarMaestros(txtBuscar.getText().trim());
            tablaMaestros.setDisable(false);
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaDeMaestro)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarMaestros();
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
                setGraphic(null);

                if (empty)
                    return;

                MaestroData maestro = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-success");
                botonEditar.setOnAction(event -> editarMaestro(maestro));
                setGraphic(botonEditar);
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
                setGraphic(null);

                if (empty)
                    return;

                MaestroData maestro = getTableView().getItems().get(getIndex());
                TableRow<MaestroData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (maestro.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarMaestro(maestro);
                            buscarMaestros();
                        });
                    });
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarMaestro(maestro);
                            buscarMaestros();
                        });
                    });
                }
                setGraphic(botonEliminar);
            }
        });

        tablaMaestros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMaestros.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno,
                columnaCorreoElectronico, columnaEditar, columnaEstatus);
        tablaMaestros.setItems(coleccionMaestros);
    }

    @FXML
    private void registrarNuevoMaestro() {
        FormularioMaestroStage formulario = new FormularioMaestroStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarMaestros();
    }

    private void editarMaestro(MaestroData maestro) {
        FormularioMaestroStage formulario = new FormularioMaestroStage(maestro);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarMaestros();
    }

    private void habilitarMaestro(MaestroData maestro) {
        cambiarEstatus("habilitar", maestro);
    }

    private void deshabilitarMaestro(MaestroData maestro) {
        cambiarEstatus("deshabilitar", maestro);
    }

    private void cambiarEstatus(String estatus, MaestroData maestro) {

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);

            //Servicios
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var actualizadorMaestro = new ActualizadorMaestro(maestroRepositorio);

            GestionarEstatusMaestro gestionarEstatusMaestro = new GestionarEstatusMaestro(
                    buscadorMaestro, actualizadorMaestro);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusMaestro.habilitarMaestro(maestro.id());
                case "deshabilitar" -> gestionarEstatusMaestro.deshabilitarMaestro(maestro.id());
            }

            transaccion.commit();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    public void buscarMaestros() {
        esBusquedaDeMaestro = false;
        txtBuscar.setText("");
        esBusquedaDeMaestro = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarMaestros(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        MaestrosData maestros;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            BuscarMaestros buscarMaestros = new BuscarMaestros(buscadorMaestro, buscadorUsuario);

            if (criterioBusqueda.isBlank()) {
                maestros = buscarMaestros.buscarTodos();
            } else {
                System.out.println("Busqueda de maestros por criterio: '" + criterioBusqueda + "'");
                maestros = buscarMaestros.buscarPorCriterio(criterioBusqueda);
            }

            cargarMaestrosEnTabla(maestros);
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
                mensaje.setTitle("Error de base de datos");
                mensaje.showAndWait();
            });
        }

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
