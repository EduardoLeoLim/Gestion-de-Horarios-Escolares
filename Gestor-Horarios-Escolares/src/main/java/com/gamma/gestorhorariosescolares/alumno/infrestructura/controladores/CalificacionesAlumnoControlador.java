package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMaestroData;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.BuscarEvaluacionesDeAlumno;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionesData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.buscar.BuscadorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia.MySql2oEvaluacionRepositorio;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar.BuscadorInscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia.MySql2oInscripcionRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
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

public class CalificacionesAlumnoControlador {

    private final Stage stage;
    private final AlumnoData alumno;
    private Temporizador temporizadorBusqueda;
    private ObservableList<EvaluacionData> coleccionEvaluaciones;

    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private TableView<EvaluacionData> tablaEvaluacionesAlumno;

    public CalificacionesAlumnoControlador(Stage stage, AlumnoData alumno) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.alumno = alumno;

    }

    public void initialize() {
        inicializarCbxPeriodoEscolar();
        buscarPeriodosEscolares();
        inicializarTabla();
        if (cbxPeriodoEscolar.getItems().size() > 0) {
            cbxPeriodoEscolar.setValue(cbxPeriodoEscolar.getItems().get(0));
        }

    }

    private void inicializarTabla() {
        coleccionEvaluaciones = FXCollections.observableArrayList();

        TableColumn<EvaluacionData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<EvaluacionData, String> columnaMateria = new TableColumn<>("Materia");
        columnaMateria.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().nombre()));
        columnaMateria.setMinWidth(150);

        TableColumn<EvaluacionData, String> columnaMaestro = new TableColumn<>("Maestro");
        columnaMaestro.setCellValueFactory(ft -> {
            ClaseMaestroData maestro = ft.getValue().maestro();
            if (maestro == null)
                return new SimpleStringProperty("Sin asignar");
            return new SimpleStringProperty(maestro.nombre());
        });
        columnaMaestro.setMinWidth(150);

        TableColumn<EvaluacionData, String> columnaCalificacion = new TableColumn<>("Calificacion");
        columnaCalificacion.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().evaluacion()));
        columnaCalificacion.setMinWidth(150);

        tablaEvaluacionesAlumno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaEvaluacionesAlumno.getColumns().addAll(columnaClave, columnaMateria, columnaMaestro, columnaCalificacion);
        tablaEvaluacionesAlumno.setItems(coleccionEvaluaciones);
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
            buscarEvaluaciones(newValue);
        });
    }

    private void buscarEvaluaciones(PeriodoEscolarData periodoEscolar) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection conexionBD = conexion.open()) {
            var inscripcionRepositorio = new MySql2oInscripcionRepositorio(conexionBD);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexionBD);
            var maestroRepositorio = new MySql2oMaestroRepositorio(conexionBD);
            var evaluacionRepositorio = new MySql2oEvaluacionRepositorio(conexionBD);

            var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorEvaluacion = new BuscadorEvaluacion(evaluacionRepositorio);

            BuscarEvaluacionesDeAlumno buscarEvaluacionesDeAlumno = new BuscarEvaluacionesDeAlumno(buscadorInscripcion, buscadorMateria, buscadorMaestro, buscadorEvaluacion);

            EvaluacionesData evaluaciones = buscarEvaluacionesDeAlumno.buscarEvaluaciones(periodoEscolar.id(), alumno.id());

            cargarEvaluacionesEnTabla(evaluaciones);
        } catch (Sql2oException ex) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Error");
            mensaje.showAndWait();
        }
    }

    private void cargarEvaluacionesEnTabla(EvaluacionesData evaluaciones) {
        coleccionEvaluaciones.clear();
        coleccionEvaluaciones.addAll(evaluaciones.evaluaciones());
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
            System.out.println(e);
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}