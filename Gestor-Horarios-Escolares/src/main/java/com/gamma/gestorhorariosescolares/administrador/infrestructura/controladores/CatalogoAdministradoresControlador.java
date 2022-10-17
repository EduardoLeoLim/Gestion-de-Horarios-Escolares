package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CatalogoAdministradoresControlador {
    private final Stage stage;
    private Temporizador temporizadorBusqueda;


    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<AdministradorData> tablaAdministradores;

    public CatalogoAdministradoresControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {

        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarAdministradores(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))//No se realiza la busqueda cuando se presionan teclas que no modifican la cadena de búsqueda.
                return;
            temporizadorBusqueda.reiniciar();
        });

        //Configuracion de la tabla
        inicializarTabla();
    }

    private void inicializarTabla() {
        //Borrar después
        var u = new UsuarioData(1, "correo", "clave", "admin");
        var a = new AdministradorData(1, "noPErsonal", "angel", "martinez", "leo lim", true, u);
        List<AdministradorData> lista = new ArrayList<>();
        lista.add(a);
        ObservableList<AdministradorData> coleccion = FXCollections.observableArrayList(lista);
        tablaAdministradores.setItems(coleccion);
        var u2 = new UsuarioData(1, "cofdfdrreo", "clafdfve", "adfdfmin");
        var a2 = new AdministradorData(1, "noPErfdfdsonal", "anfdfdgel", "marfdfdtinez", "leofdfd lim", true, u2);
        tablaAdministradores.setItems(coleccion);
        coleccion.addAll(new ArrayList<AdministradorData>());
        //Borrar después

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

        TableColumn<AdministradorData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<AdministradorData, String> columnaEditar = new TableColumn<>("");
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
                    botonEditar.getStyleClass().addAll("b","btn-success");
                    botonEditar.setOnAction(event -> editarAdministrador(administrador));
                    setGraphic(botonEditar);
                }
            }
        });

        TableColumn<AdministradorData, String> columnaEliminar = new TableColumn<>("");
        columnaEliminar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                if (empty) {
                    setGraphic(null);
                } else {
                    AdministradorData administrador = getTableView().getItems().get(getIndex());
                    Button botonEliminar = new Button("Eliminar");
                    botonEliminar.getStyleClass().addAll("b","btn-danger");
                    botonEliminar.setOnAction(event -> eliminarAdministrador(administrador));
                    setGraphic(botonEliminar);
                }
            }
        });

        tablaAdministradores.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno,
                columnaApellidoMaterno, columnaCorreoElectronico, columnaEditar, columnaEliminar);
    }

    private void buscarAdministradores(String criterioBuscqueda) {
        System.out.println("Busqueda de administradores por criterio: '" + criterioBuscqueda + "'");
    }

    public void editarAdministrador(AdministradorData administrador) {
//        var a = new Stage();
//        a.initModality(Modality.WINDOW_MODAL);
//        a.initOwner(stage);
//        a.showAndWait();
    }

    public void eliminarAdministrador(AdministradorData administrador) {

    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
