package com.gamma.gestorhorariosescolares.materia.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.BuscarMaterias;
import com.gamma.gestorhorariosescolares.materia.aplicacion.GestionarEstatusMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriasData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.actualizar.ActualizadorMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.materia.infrestructura.stages.FormularioMateriaStage;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.buscar.BuscadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia.MySql2oPlanEstudioRepositorio;
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

public class CatalogoMateriasControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<MateriaData> coleccionMaterias;
    private boolean esBusquedaMateria;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<MateriaData> tablaMaterias;

    public CatalogoMateriasControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
    }

    @FXML
    public void initialize() {

        //Configuración de búsqueda
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Búsqueda de materias
            Platform.runLater(() -> {
                tablaMaterias.setDisable(true);
                buscarMaterias(txtBuscar.getText().trim());
                tablaMaterias.setDisable(false);
            });
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaMateria)
                return;
            temporizadorBusqueda.reiniciar();
        });

        //Configuración de tabla
        inicializarTabla();
        buscarMaterias();
    }

    private void inicializarTabla() {
        coleccionMaterias = FXCollections.observableArrayList();

        TableColumn<MateriaData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<MateriaData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<MateriaData, String> columnaHorasPracticas = new TableColumn<>("Horas prácticas");
        columnaHorasPracticas.setCellValueFactory(ft -> new SimpleStringProperty("" + ft.getValue().horasPracticas()));
        columnaHorasPracticas.setMinWidth(150);

        TableColumn<MateriaData, String> columnaHorasTeoricas = new TableColumn<>("Horas teóricas");
        columnaHorasTeoricas.setCellValueFactory(ft -> new SimpleStringProperty("" + ft.getValue().horasTeoricas()));
        columnaHorasTeoricas.setMinWidth(150);

        TableColumn<MateriaData, String> columnaPlanGrado = new TableColumn<>("Plan - Grado");
        columnaPlanGrado.setCellValueFactory(ft -> {
            MateriaData materiaData = ft.getValue();
            String texto = materiaData.planEstudio().nombre() + " - " + materiaData.grado().nombre();
            return new SimpleStringProperty(texto);
        });
        columnaPlanGrado.setMinWidth(200);

        TableColumn<MateriaData, String> columnaEditar = new TableColumn<>();
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

                MateriaData materia = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-success");
                botonEditar.setOnAction(event -> editarMateria(materia));
                setGraphic(botonEditar);
            }
        });

        TableColumn<MateriaData, String> columnaEstatus = new TableColumn<>();
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

                MateriaData materia = getTableView().getItems().get(getIndex());
                TableRow<MateriaData> fila = getTableRow();
                fila.setDisable(false);

                Button botonEstatus = new Button();
                botonEstatus.setPrefWidth(Double.MAX_VALUE);
                if (materia.estatus()) {
                    botonEstatus.setText("Deshabilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-danger");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            deshabilitarMateria(materia);
                            buscarMaterias();
                        });
                    });
                } else {
                    botonEstatus.setText("Habilitar");
                    botonEstatus.getStyleClass().addAll("b", "btn-primary");
                    botonEstatus.setOnAction(event -> {
                        fila.setDisable(true);
                        Platform.runLater(() -> {
                            habilitarMateria(materia);
                            buscarMaterias();
                        });
                    });
                }
                setGraphic(botonEstatus);
            }
        });

        tablaMaterias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMaterias.getColumns().addAll(columnaClave, columnaNombre, columnaHorasPracticas, columnaHorasTeoricas,
                columnaPlanGrado, columnaEditar, columnaEstatus);
        tablaMaterias.setItems(coleccionMaterias);
    }

    @FXML
    private void registrarNuevaMateria() {
        var formulario = new FormularioMateriaStage();
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarMaterias();
    }

    private void editarMateria(MateriaData materia) {
        var formulario = new FormularioMateriaStage(materia);
        formulario.initOwner(stage);
        formulario.showAndWait();
        buscarMaterias();
    }

    private void habilitarMateria(MateriaData materia) {
        cambiarEstatus("habilitar", materia);
    }

    private void deshabilitarMateria(MateriaData materia) {
        cambiarEstatus("deshabilitar", materia);
    }

    private void cambiarEstatus(String estatus, MateriaData materia) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);

            //Servicios
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var actualizadorMateria = new ActualizadorMateria(materiaRepositorio);

            GestionarEstatusMateria gestionarEstatusMateria = new GestionarEstatusMateria(buscadorMateria, actualizadorMateria);

            switch (estatus.toLowerCase()) {
                case "habilitar" -> gestionarEstatusMateria.habilitarMateria(materia.id());
                case "deshabilitar" -> gestionarEstatusMateria.deshabilitarMateria(materia.id());
            }

            transaccion.commit();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        }
    }

    private void buscarMaterias() {
        esBusquedaMateria = false;
        txtBuscar.setText("");
        temporizadorBusqueda.reiniciar();
        esBusquedaMateria = true;
    }

    private void buscarMaterias(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        MateriasData materias;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);
            var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);
            var planEstudioRepositorio = new MySql2oPlanEstudioRepositorio(transaccion);

            //Servicios
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);
            var buscadorPlanEstudio = new BuscadorPlanEstudio(planEstudioRepositorio);

            BuscarMaterias buscarMaterias = new BuscarMaterias(buscadorMateria, buscadorGrado, buscadorPlanEstudio);

            if (criterioBusqueda.isBlank()) {
                materias = buscarMaterias.buscarTodos();
            } else {
                System.out.println("Busqueda de materias por criterio: '" + criterioBusqueda + "'");
                materias = buscarMaterias.buscarPorCriterio(criterioBusqueda);
            }

            cargarMateriaEnTabla(materias);
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Base de datos no disponible.\nIntentalo más tarde", ButtonType.OK).showAndWait();
        }
    }

    private void cargarMateriaEnTabla(MateriasData listaMaterias) {
        if (listaMaterias == null)
            return;

        coleccionMaterias.clear();
        coleccionMaterias.addAll(listaMaterias.materias());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
