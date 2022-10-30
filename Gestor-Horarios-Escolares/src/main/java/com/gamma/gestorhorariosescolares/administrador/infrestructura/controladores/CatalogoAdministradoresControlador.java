package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradoresData;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.BuscarAdministradores;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.GestionarEstatusAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar.ActualizadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.persistencia.MySql2oAdministradorRespositorio;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.stages.FormularioAdministradorStage;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class CatalogoAdministradoresControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<AdministradorData> colleccionAdministradores;
    private Boolean esBusquedaDeAdministrador;

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<AdministradorData> tablaAdministradores;

    public CatalogoAdministradoresControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        esBusquedaDeAdministrador = true;
    }

    @FXML
    public void initialize() {

        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarAdministradores(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) && esBusquedaDeAdministrador)//No se realiza la busqueda cuando se presionan teclas que no modifican la cadena de búsqueda.
                return;
            temporizadorBusqueda.reiniciar();
        });

        //Configuracion de la tabla
        inicializarTabla();
        buscarAdministradores();
    }

    private void inicializarTabla() {
        tablaAdministradores.setEditable(false);
        colleccionAdministradores = FXCollections.observableArrayList();

        TableColumn<AdministradorData, String> columnaNoPersonal = new TableColumn<>("No. Personal");
        columnaNoPersonal.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().noPersonal()));
        columnaNoPersonal.setMinWidth(150);

        TableColumn<AdministradorData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<AdministradorData, String> columnaApellidoPaterno = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoPaterno()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<AdministradorData, String> columnaApellidoMaterno = new TableColumn<>("Apellido Materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoMaterno()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<AdministradorData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<AdministradorData, String> columnaEditar = new TableColumn<>("");
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
                    AdministradorData administrador = getTableView().getItems().get(getIndex());
                    Button botonEditar = new Button("Editar");
                    botonEditar.setPrefWidth(Double.MAX_VALUE);
                    botonEditar.getStyleClass().addAll("b", "btn-success");
                    botonEditar.setOnAction(event -> editarAdministrador(administrador));
                    setGraphic(botonEditar);
                }
            }
        });

        TableColumn<AdministradorData, String> columnaEstatus = new TableColumn<>("");
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
                AdministradorData administrador = getTableView().getItems().get(getIndex());
                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (administrador.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> deshabilitarAdministrador(administrador));
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> habilitarAdministrador(administrador));
                }
                setGraphic(botonEliminar);
            }
        });

        tablaAdministradores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaAdministradores.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno,
                columnaApellidoMaterno, columnaCorreoElectronico, columnaEditar, columnaEstatus);
        tablaAdministradores.setItems(colleccionAdministradores);
    }

    @FXML
    private void registrarNuevoAdministrador() {
        var formulario = new FormularioAdministradorStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarAdministradores();
    }

    public void editarAdministrador(AdministradorData administrador) {
        var formulario = new FormularioAdministradorStage(administrador);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarAdministradores();
    }

    public void habilitarAdministrador(AdministradorData administrador) {
        cambiarEstatus("habilitar", administrador);
    }

    public void deshabilitarAdministrador(AdministradorData administrador) {
        cambiarEstatus("deshabilitar", administrador);
    }

    private void cambiarEstatus(String estatus, AdministradorData administrador) {

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var administradorRepositorio = new MySql2oAdministradorRespositorio(transaccion);

            //Servicios
            var buscadorAdministrador = new BuscadorAdministrador(administradorRepositorio);
            var actualizadorAdministrador = new ActualizadorAdministrador(administradorRepositorio);

            GestionarEstatusAdministrador gestionarEstatusAdministrador = new GestionarEstatusAdministrador(
                    buscadorAdministrador, actualizadorAdministrador);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusAdministrador.habilitarAdministrador(administrador.id());
                case "deshabilitar" -> gestionarEstatusAdministrador.deshabilitarAdministrador(administrador.id());
            }

            transaccion.commit();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } finally {
            buscarAdministradores();
        }
    }

    private void buscarAdministradores() {
        esBusquedaDeAdministrador = false;
        txtBuscar.setText("");
        esBusquedaDeAdministrador = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarAdministradores(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        AdministradoresData administradores;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //inicializar repositorios
            var administradorRepositorio = new MySql2oAdministradorRespositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Inicializar servicios
            var buscadorAdministrador = new BuscadorAdministrador(administradorRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            //Inicializar caso de uso
            BuscarAdministradores buscarAdministradores = new BuscarAdministradores(buscadorAdministrador, buscadorUsuario);

            if (criterioBusqueda.isBlank()) {
                administradores = buscarAdministradores.buscarTodos();
            } else {
                System.out.println("Busqueda de administradores por criterio: '" + criterioBusqueda + "'");
                administradores = buscarAdministradores.buscarPorCriterio(criterioBusqueda);
            }

            cargarAdministradoresEnTabla(administradores);
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        }
    }

    private void cargarAdministradoresEnTabla(AdministradoresData administradores) {
        if (administradores == null)
            return;

        colleccionAdministradores.clear();
        colleccionAdministradores.addAll(administradores.administradores());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}