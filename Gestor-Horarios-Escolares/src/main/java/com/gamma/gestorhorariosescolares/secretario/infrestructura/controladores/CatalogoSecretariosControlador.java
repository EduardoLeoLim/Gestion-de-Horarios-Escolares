package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretarioData;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretariosData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CatalogoSecretariosControlador {
    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    private ObservableList<SecretarioData> coleccionSecretarios;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<SecretarioData> tablaSecretarios;

    public CatalogoSecretariosControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarSecretarios(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))//No se realiza la busqueda cuando se presionan teclas que no modifican la cadena de búsqueda.
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTabla();
        //Buscar y agregar secretarios a tabla
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

    public void editarSecretario(SecretarioData secretario) {

    }

    public void habilitarSecretario(SecretarioData secretario) {

    }

    public void deshabilitarSecretario(SecretarioData secretarioData) {

    }

    private void buscarSecretarios(String criterioBuscqueda) {
        System.out.println("Busqueda de secretarios por criterio: '" + criterioBuscqueda + "'");
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
