package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.BuscarSecretarios;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.GestionarEstatusSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretarioData;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretariosData;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar.ActualizadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.buscar.BuscadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.persistencia.MySql2oSecretarioRepositorio;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.stages.FormularioSecretarioStage;
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

public class CatalogoSecretariosControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<SecretarioData> coleccionSecretarios;
    private boolean esBusquedaDeSecretario;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<SecretarioData> tablaSecretarios;

    public CatalogoSecretariosControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        esBusquedaDeSecretario = true;
    }

    @FXML
    public void initialize() {
        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarSecretarios(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaDeSecretario)//No se realiza la busqueda cuando se presionan teclas que no modifican la cadena de búsqueda.
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        buscarSecretarios();
    }

    private void inicializarTabla() {
        coleccionSecretarios = FXCollections.observableArrayList();

        TableColumn<SecretarioData, String> columnaNoPersonal = new TableColumn<>("No. Personal");
        columnaNoPersonal.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().noPersonal()));
        columnaNoPersonal.setMinWidth(150);

        TableColumn<SecretarioData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<SecretarioData, String> columnaApellidoPaterno = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoPaterno()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<SecretarioData, String> columnaApellidoMaterno = new TableColumn<>("Apellido Materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoMaterno()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<SecretarioData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<SecretarioData, String> columnaEditar = new TableColumn<>("");
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
                    SecretarioData secretario = getTableView().getItems().get(getIndex());
                    Button botonEditar = new Button("Editar");
                    botonEditar.setPrefWidth(Double.MAX_VALUE);
                    botonEditar.getStyleClass().addAll("b", "btn-success");
                    botonEditar.setOnAction(event -> editarSecretario(secretario));
                    setGraphic(botonEditar);
                }
            }
        });

        TableColumn<SecretarioData, String> columnaEstatus = new TableColumn<>("");
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
                SecretarioData secretario = getTableView().getItems().get(getIndex());
                Button botonEliminar = new Button();
                botonEliminar.setPrefWidth(Double.MAX_VALUE);
                if (secretario.estatus()) {
                    botonEliminar.setText("Deshabilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-danger");
                    botonEliminar.setOnAction(event -> deshabilitarSecretario(secretario));
                } else {
                    botonEliminar.setText("Habilitar");
                    botonEliminar.getStyleClass().addAll("b", "btn-primary");
                    botonEliminar.setOnAction(event -> habilitarSecretario(secretario));
                }
                setGraphic(botonEliminar);
            }
        });

        tablaSecretarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaSecretarios.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno,
                columnaCorreoElectronico, columnaEditar, columnaEstatus);
        tablaSecretarios.setItems(coleccionSecretarios);
    }

    @FXML
    private void registrarNuevoSecretario() {
        var formulario = new FormularioSecretarioStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarSecretarios();
    }

    public void editarSecretario(SecretarioData secretario) {
        var formulario = new FormularioSecretarioStage(secretario);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarSecretarios();
    }

    public void habilitarSecretario(SecretarioData secretario) {
        cambiarEstatus("habilitar", secretario);
    }

    public void deshabilitarSecretario(SecretarioData secretario) {
        cambiarEstatus("deshabilitar", secretario);
    }

    private void cambiarEstatus(String estatus, SecretarioData secretario) {

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var secretarioRepositorio = new MySql2oSecretarioRepositorio(transaccion);

            //Servicios
            var buscadorSecretario = new BuscadorSecretario(secretarioRepositorio);
            var actualizadorSecretario = new ActualizadorSecretario(secretarioRepositorio);

            GestionarEstatusSecretario gestionarEstatusSecretario = new GestionarEstatusSecretario(
                    buscadorSecretario,
                    actualizadorSecretario
            );

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusSecretario.habilitarSecretario(secretario.id());
                case "deshabilitar" -> gestionarEstatusSecretario.deshabilitarSecretario(secretario.id());
            }

            transaccion.commit();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } finally {
            buscarSecretarios();
        }
    }

    private void buscarSecretarios() {
        esBusquedaDeSecretario = false;
        txtBuscar.setText("");
        esBusquedaDeSecretario = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarSecretarios(String criterioBusqueda) {


        if (criterioBusqueda == null)
            criterioBusqueda = "";

        SecretariosData secretarios;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //inicializar repositorios
            var secretarioRepositorio = new MySql2oSecretarioRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Inicializar servicios
            var buscadorSecretario = new BuscadorSecretario(secretarioRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            //Inicializar caso de uso
            BuscarSecretarios buscarSecretarios = new BuscarSecretarios(buscadorSecretario, buscadorUsuario);

            if (criterioBusqueda.isBlank()) {
                secretarios = buscarSecretarios.buscarTodos();
            } else {
                System.out.println("Busqueda de secretarios por criterio: '" + criterioBusqueda + "'");
                secretarios = buscarSecretarios.buscarPorCriterio(criterioBusqueda);
            }

            cargarSecretariosEnTabla(secretarios);
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al buscar secretarios");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
        }


    }

    private void cargarSecretariosEnTabla(SecretariosData listaSecretarios) {
        if (listaSecretarios == null)
            return;
        coleccionSecretarios.clear();
        coleccionSecretarios.addAll(listaSecretarios.secretarios());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
