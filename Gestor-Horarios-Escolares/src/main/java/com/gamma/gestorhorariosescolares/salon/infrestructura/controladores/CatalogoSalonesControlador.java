package com.gamma.gestorhorariosescolares.salon.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.BuscarSalones;
import com.gamma.gestorhorariosescolares.salon.aplicacion.GestionarEstatusSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonesData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar.ActualizadorSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.buscar.BuscadorSalon;
import com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia.MySql2oSalonRepositorio;
import com.gamma.gestorhorariosescolares.salon.infrestructura.stages.FormularioSalonStage;
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

public class CatalogoSalonesControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<SalonData> coleccionSalones;
    private boolean esBusquedaSalon;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<SalonData> tablaSalones;

    public CatalogoSalonesControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaSalon = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //busca salones en hilo seguro
            Platform.runLater(() -> buscarSalones(txtBuscar.getText().trim()));
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaSalon)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarSalones();
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

                setGraphic(botonEditar);
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
                botonEstatus.setPrefWidth(Double.MAX_VALUE);
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

    @FXML
    private void registrarNuevoSalon() {
        var formulario = new FormularioSalonStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarSalones();
    }

    private void editarSalon(SalonData salon) {
        var formulario = new FormularioSalonStage(salon);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarSalones();
    }

    private void habilitarSalon(SalonData salon) {
        cambiarEstatus("habilitar", salon);
    }

    private void deshabilitarSalon(SalonData salon) {
        cambiarEstatus("deshabilitar", salon);
    }

    private void cambiarEstatus(String estatus, SalonData salon) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var salonRepositorio = new MySql2oSalonRepositorio(transaccion);

            //Servicios
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var actualizadorSalon = new ActualizadorSalon(salonRepositorio);

            GestionarEstatusSalon gestionarEstatusSalon = new GestionarEstatusSalon(buscadorSalon, actualizadorSalon);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusSalon.habilitarSalon(salon.id());
                case "deshabilitar" -> gestionarEstatusSalon.deshabilitarSalon(salon.id());
            }

            transaccion.commit();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Edificio no encontrado");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        } finally {
            buscarSalones();
        }
    }

    private void buscarSalones() {
        esBusquedaSalon = false;
        txtBuscar.setText("");
        esBusquedaSalon = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarSalones(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        SalonesData salones;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var salonRepositorio = new MySql2oSalonRepositorio(transaccion);
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarSalones buscarSalones = new BuscarSalones(buscadorSalon, buscadorEdificio);

            if (criterioBusqueda.isBlank()) {
                salones = buscarSalones.buscarTodos();
            } else {
                System.out.println("Busqueda de salones por criterio: '" + criterioBusqueda + "'");
                salones = buscarSalones.buscarPorCriterio(criterioBusqueda);
            }

            cargarSalonesEnTabla(salones);
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        }
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
