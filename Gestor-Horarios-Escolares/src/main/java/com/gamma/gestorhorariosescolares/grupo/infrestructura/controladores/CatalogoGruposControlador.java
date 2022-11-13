package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.BuscarGrupos;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GruposData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
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

public class CatalogoGruposControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;
    private ObservableList<PeriodoEscolarData> coleccionPeriodosEscolares;
    private ObservableList<GrupoData> coleccionGrupos;
    private boolean esBusquedaGrupo;

    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private TableView<GrupoData> tablaGrupos;

    public CatalogoGruposControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.esBusquedaGrupo = true;
    }

    @FXML
    public void initialize() {
        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            String criterioBusqueda = txtBuscar.getText();
            PeriodoEscolarData periodoEscolar = cbxPeriodoEscolar.getValue();
            Platform.runLater(() -> {
                tablaGrupos.setDisable(true);
                buscarGrupos(criterioBusqueda, periodoEscolar);
                tablaGrupos.setDisable(false);
            });
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaGrupo)
                return;
            temporizadorBusqueda.reiniciar();
        });
        cbxPeriodoEscolar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarComboBoxPeriodoEscolar();
        inicializarTabla();

        buscarPeriodosEscolares();
        buscarGrupos();
    }

    private void inicializarComboBoxPeriodoEscolar() {
        cbxPeriodoEscolar.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                if (empty || periodoEscolar == null)
                    return;

                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });

        cbxPeriodoEscolar.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                if (empty || periodoEscolar == null)
                    return;

                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });

        coleccionPeriodosEscolares = FXCollections.observableArrayList();
        cbxPeriodoEscolar.setItems(coleccionPeriodosEscolares);
    }

    private void inicializarTabla() {
        coleccionGrupos = FXCollections.observableArrayList();

        TableColumn<GrupoData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<GrupoData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<GrupoData, String> columnaPeriodoEscolar = new TableColumn<>("Periodo Escolar");
        columnaPeriodoEscolar.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().periodoEscolar().nombre()));
        columnaPeriodoEscolar.setMinWidth(200);

        TableColumn<GrupoData, String> columnaAlumnos = new TableColumn<>("Alumnos");
        columnaAlumnos.setCellValueFactory(ft -> new SimpleStringProperty("" + ft.getValue().numAlumnos()));
        columnaAlumnos.setMinWidth(150);

        TableColumn<GrupoData, String> columnaEditar = new TableColumn<>();
        columnaEditar.setMinWidth(80);
        columnaEditar.setMaxWidth(80);
        columnaEditar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty || item == null)
                    return;

                GrupoData grupo = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Editar");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-succes");
                botonEditar.setOnAction(event -> editarGrupo(grupo));
                setGraphic(botonEditar);
            }
        });

        tablaGrupos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaGrupos.getColumns().addAll(columnaClave, columnaNombre, columnaPeriodoEscolar, columnaAlumnos, columnaEditar);
        tablaGrupos.setItems(coleccionGrupos);
    }

    @FXML
    private void registrarNuevoGrupo() {

    }

    private void editarGrupo(GrupoData grupo) {

    }

    public void buscarGrupos() {
        esBusquedaGrupo = false;
        txtBuscar.setText("");
        cbxPeriodoEscolar.setValue(null);
        esBusquedaGrupo = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarGrupos(String criterioBusqueda, PeriodoEscolarData periodoEscolar) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var grupoRepositorio = new MySql2oGrupoRepositorio(transaccion);
            var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarGrupos buscarGrupos = new BuscarGrupos(buscadorGrupo, buscadorGrado, buscadorPeriodoEscolar);

            Integer idPeriodoEscolar = periodoEscolar == null ? null : periodoEscolar.id();
            GruposData grupos = buscarGrupos.buscarPorCoincidencia(criterioBusqueda, criterioBusqueda, null, idPeriodoEscolar);
            cargarGruposEnTabla(grupos);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    private void buscarPeriodosEscolares() {
        esBusquedaGrupo = false;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);

            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarTodos();


            coleccionPeriodosEscolares.clear();
            coleccionPeriodosEscolares.addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException e) {
            cbxPeriodoEscolar.setValue(null);
            coleccionPeriodosEscolares.clear();
        } finally {
            esBusquedaGrupo = true;
        }
    }

    private void cargarGruposEnTabla(GruposData grupos) {
        if (grupos == null)
            return;

        coleccionGrupos.clear();
        coleccionGrupos.addAll(grupos.grupos());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}