package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.stages.CalificacionAlumnoStage;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaMaestroData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.BuscarEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionInscripcionData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionesData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionesInscripcionData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.buscar.BuscadorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia.MySql2oEvaluacionRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.stages.InformacionGrupoStage;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar.BuscadorInscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia.MySql2oInscripcionRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class InformacionGrupoControlador {

    private final Stage stage;

    private final ClaseMateriaMaestroData clase;



    private ObservableList coleccionEvaluaciones;



    @FXML
    private TableView<EvaluacionInscripcionData> tablaGrupo;

    @FXML
    private Label txtMateria;

    public InformacionGrupoControlador(Stage stage, ClaseMateriaMaestroData clase) {
        if(stage == null)
            throw new NullPointerException();
        if (clase == null)
            throw new NullPointerException();
        this.stage = stage;
        stage.setTitle("Informacion del grupo");
        this.clase = clase;


    }

    public void initialize(){
        txtMateria.setText(clase.materia().nombre());
        inicializarTabla();
        buscarEvaluaciones(clase);


    }

    private void inicializarTabla(){
        coleccionEvaluaciones = FXCollections.observableArrayList();

        TableColumn<EvaluacionInscripcionData, String> columnaMatricula = new TableColumn<>("Matricula");
        columnaMatricula.setCellValueFactory(ft-> new SimpleStringProperty(ft.getValue().alumno().matricula()));
        columnaMatricula.setMinWidth(150);

        TableColumn<EvaluacionInscripcionData, String> columnaAlumno = new TableColumn<>("Alumno");
        columnaAlumno.setCellValueFactory(ft-> new SimpleStringProperty(ft.getValue().alumno().nombre()
                + " " + ft.getValue().alumno().apellidoPaterno() + " " + ft.getValue().alumno().apellidoMaterno()));
        columnaAlumno.setMinWidth(150);

        TableColumn<EvaluacionInscripcionData, String> columnaCalificacion = new TableColumn<>("Calificacion");
        columnaCalificacion.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().calificacion()));
        columnaCalificacion.setMinWidth(150);

        TableColumn<EvaluacionInscripcionData, String> columnaTipoExamen = new TableColumn<>("Tipo de Examen");
        columnaTipoExamen.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().tipo()));
        columnaTipoExamen.setMinWidth(150);

        TableColumn<EvaluacionInscripcionData, String> columnaAsignar = new TableColumn<>();
        columnaAsignar.setMinWidth(180);
        columnaAsignar.setMaxWidth(180);
        columnaAsignar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty)
                    return;

                EvaluacionInscripcionData asignacion = getTableView().getItems().get(getIndex());
                Button botonAsignar = new Button("Asignar calificacion");
                botonAsignar.setPrefWidth(Double.MAX_VALUE);
                botonAsignar.getStyleClass().addAll("b", "btn-primary");
                botonAsignar.setOnAction(event -> asignarCalificacion(asignacion));
                setGraphic(botonAsignar);
            }
        });

        tablaGrupo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaGrupo.getColumns().addAll(columnaMatricula, columnaAlumno, columnaCalificacion, columnaTipoExamen, columnaAsignar);
        tablaGrupo.setItems(coleccionEvaluaciones);

    }

    private void asignarCalificacion(EvaluacionInscripcionData evaluacion) {
        var ventanaAsignacionCalificacion = new CalificacionAlumnoStage(evaluacion);
        ventanaAsignacionCalificacion.initOwner(stage);
        ventanaAsignacionCalificacion.showAndWait();
        buscarEvaluaciones(clase);



    }

    private void buscarEvaluaciones(ClaseMateriaMaestroData clase){
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try(Connection conexionBD = conexion.open()){
            var claseRepositorio = new MySql2oClaseRepositorio(conexionBD);
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexionBD);
            var inscripcionRepositorio = new MySql2oInscripcionRepositorio(conexionBD);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexionBD);
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(conexionBD);
            var evaluacionRepositorio = new MySql2oEvaluacionRepositorio(conexionBD);

            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var buscadorEvaluacion = new BuscadorEvaluacion(evaluacionRepositorio);

            BuscarEvaluacion buscarEvaluacion = new BuscarEvaluacion(buscadorClase, buscadorGrupo, buscadorInscripcion,buscadorMateria, buscadorAlumno, buscadorEvaluacion );

            EvaluacionesInscripcionData evaluacionesInscripcionData = buscarEvaluacion.buscarEvaluaciones(clase.idClase());

            cargarEvaluacionesEnTabla(evaluacionesInscripcionData);

        }catch (Sql2oException ex){
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();


        } catch (RecursoNoEncontradoException e) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Error");
            mensaje.showAndWait();

        }

    }

    private void cargarEvaluacionesEnTabla(EvaluacionesInscripcionData evaluaciones){
        coleccionEvaluaciones.clear();
        coleccionEvaluaciones.addAll(evaluaciones.evaluaciones());

    }








}
