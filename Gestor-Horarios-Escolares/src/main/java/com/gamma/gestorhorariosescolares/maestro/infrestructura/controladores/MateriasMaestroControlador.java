package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.BuscarClasesDeMaestro;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaMaestroData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClasesMateriaMaestroData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.stages.InformacionGrupoStage;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class MateriasMaestroControlador {

    private final Stage stage;
    private final MaestroData maestro;
    private Temporizador temporizadorBusqueda;
    private ObservableList<ClaseMateriaMaestroData> coleccionClasesMaestro;

    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;

    @FXML
    private TableView<ClaseMateriaMaestroData> tablaMateriasMaestro;


    public MateriasMaestroControlador(Stage stage, MaestroData maestro) {
        if (maestro == null)
            throw new NullPointerException();

        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.maestro = maestro;

    }

    @FXML
    public void initialize() {

        inicializarCbxPeriodoEscolar();
        buscarPeriodosEscolares();
        inicializarTabla();
        if (cbxPeriodoEscolar.getItems().size() > 0) {
            cbxPeriodoEscolar.setValue(cbxPeriodoEscolar.getItems().get(0));
        }
    }

    private void inicializarCbxPeriodoEscolar() {
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

        cbxPeriodoEscolar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            buscarMaterias(newValue.id(), this.maestro.id());
        });
    }

    private void buscarPeriodosEscolares() {
        cbxPeriodoEscolar.getItems().clear();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);

            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarTodos();

            cbxPeriodoEscolar.getItems().addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    private void inicializarTabla() {
        coleccionClasesMaestro = FXCollections.observableArrayList();

        TableColumn<ClaseMateriaMaestroData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<ClaseMateriaMaestroData, String> columnaMateria = new TableColumn<>("Materia");
        columnaMateria.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().nombre()));
        columnaMateria.setMinWidth(150);

        TableColumn<ClaseMateriaMaestroData, String> columnaGrupo = new TableColumn<>();
        columnaGrupo.setMinWidth(120);
        columnaGrupo.setMaxWidth(120);
        columnaGrupo.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty)
                    return;

                ClaseMateriaMaestroData grupo = getTableView().getItems().get(getIndex());
                Button botonEditar = new Button("Ver grupo");
                botonEditar.setPrefWidth(Double.MAX_VALUE);
                botonEditar.getStyleClass().addAll("b", "btn-primary");
                botonEditar.setOnAction(event -> verGrupo(grupo));
                setGraphic(botonEditar);
            }
        });

        tablaMateriasMaestro.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMateriasMaestro.getColumns().addAll(columnaClave, columnaMateria, columnaGrupo);
        tablaMateriasMaestro.setItems(coleccionClasesMaestro);


    }

    private void verGrupo(ClaseMateriaMaestroData clase) {
        var ventanaInformacionGrupo = new InformacionGrupoStage(clase);
        ventanaInformacionGrupo.initOwner(stage);
        ventanaInformacionGrupo.showAndWait();

        buscarPeriodosEscolares();
    }

    public void buscarMaterias(Integer periodoEscolar, Integer idMaestro) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection conexionBD = conexion.open()) {
            var claseRepositorio = new MySql2oClaseRepositorio(conexionBD);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexionBD);

            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);

            BuscarClasesDeMaestro buscarClasesDeMaestro = new BuscarClasesDeMaestro(buscadorClase, buscadorMateria);

            ClasesMateriaMaestroData materiasMaestro = buscarClasesDeMaestro.buscar(periodoEscolar, idMaestro);

            cargarMateriasEnTabla(materiasMaestro);
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Error");
            mensaje.showAndWait();
        } catch (Sql2oException ex) {
            System.out.println(ex);
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        }
    }

    private void cargarMateriasEnTabla(ClasesMateriaMaestroData clases) {
        coleccionClasesMaestro.clear();
        coleccionClasesMaestro.addAll(clases.clasesMaestro());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}