package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.clase.aplicacion.BuscarClasesPorGrupo;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClasesGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.BuscarGrupos;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GestionarInscripcionesGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar.ActualizadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.BuscarInscripcionesPorGrupo;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.InscripcionGrupoData;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.InscripcionesGrupoData;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar.BuscadorInscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia.MySql2oInscripcionRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroClaseData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
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

import java.util.Optional;

public class DetallesGrupoControlador {

    private final Stage stage;
    private final Integer idGrupo;
    private Temporizador temporizador;
    private ObservableList<ClaseGrupoData> colleccionClases;
    private ObservableList<InscripcionGrupoData> coleccionInscripciones;

    //Datos generales
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPeriodoEscolar;
    @FXML
    private TextField txtPlanEstudio;
    @FXML
    private TextField txtGrado;

    //Clases
    @FXML
    private TableView<ClaseGrupoData> tablaClases;

    //Alumnos
    @FXML
    private Button btnAgregarAlumno;
    @FXML
    private TableView<InscripcionGrupoData> tablaAlumnos;

    public DetallesGrupoControlador(Stage stage, int idGrupo) {
        this.stage = stage;
        stage.setOnHidden(event -> liberarRecursos());
        this.idGrupo = idGrupo;
    }

    @FXML
    private void initialize() {
        inicializarTablaClases();
        inicializarTablaAlumnos();

        temporizador = new Temporizador(1, temporizador1 -> cargarDetallesGrupo());
        temporizador.reiniciar();
    }

    private void inicializarTablaClases() {
        colleccionClases = FXCollections.observableArrayList();

        TableColumn<ClaseGrupoData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().clave()));
        columnaClave.setMinWidth(120);

        TableColumn<ClaseGrupoData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().nombre()));
        columnaNombre.setMinWidth(120);

        TableColumn<ClaseGrupoData, String> columnaHorasTeoricas = new TableColumn<>("Horas teóricas");
        columnaHorasTeoricas.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().horasTeoricas() + " horas"));
        columnaHorasTeoricas.setMinWidth(120);

        TableColumn<ClaseGrupoData, String> columnaHorasPracticas = new TableColumn<>("Horas prácticas");
        columnaHorasPracticas.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().materia().horasPracticas() + " horas"));
        columnaHorasPracticas.setMinWidth(120);

        TableColumn<ClaseGrupoData, String> columnaMestro = new TableColumn<>("Maestro");
        columnaMestro.setCellValueFactory(ft -> {
            MaestroClaseData maestro = ft.getValue().maestro();
            if (maestro == null)
                return new SimpleStringProperty("Sin asignar");
            return new SimpleStringProperty(maestro.nombre());
        });
        columnaMestro.setMinWidth(150);

        TableColumn<ClaseGrupoData, String> columnaAsignar = new TableColumn<>();
        columnaAsignar.setMinWidth(90);
        columnaAsignar.setMaxWidth(90);
        columnaAsignar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);

                if (empty)
                    return;

                ClaseGrupoData clase = getTableView().getItems().get(getIndex());

                Button btnAsignarMaestro = new Button("Asignar");
                btnAsignarMaestro.setPrefWidth(Double.MAX_VALUE);
                btnAsignarMaestro.getStyleClass().addAll("b", "btn-primary");
                btnAsignarMaestro.setOnAction(event -> asignarMaestro(clase));
                setGraphic(btnAsignarMaestro);
            }
        });

        tablaClases.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaClases.getColumns().addAll(columnaClave, columnaNombre, columnaHorasTeoricas, columnaHorasPracticas,
                columnaMestro, columnaAsignar);
        tablaClases.setItems(colleccionClases);

    }

    private void inicializarTablaAlumnos() {
        coleccionInscripciones = FXCollections.observableArrayList();

        TableColumn<InscripcionGrupoData, String> columnaMatricula = new TableColumn<>("Matrícula");
        columnaMatricula.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().alumno().matricula()));
        columnaMatricula.setMinWidth(150);

        TableColumn<InscripcionGrupoData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().alumno().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<InscripcionGrupoData, String> columnaApellidoPaterno = new TableColumn<>("Apellido paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().alumno().nombre()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<InscripcionGrupoData, String> columnaApellidoMaterno = new TableColumn<>("Apellido materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().alumno().nombre()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<InscripcionGrupoData, String> columnaRemover = new TableColumn<>();
        columnaRemover.setMinWidth(90);
        columnaRemover.setMaxWidth(90);
        columnaRemover.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);

                if (empty)
                    return;

                InscripcionGrupoData inscripcion = getTableView().getItems().get(getIndex());

                Button btnRemover = new Button("Remover");
                btnRemover.setPrefWidth(Double.MAX_VALUE);
                btnRemover.getStyleClass().addAll("b", "btn-danger");
                btnRemover.setOnAction(event -> removerAlumno(inscripcion));
                setGraphic(btnRemover);
            }
        });

        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaAlumnos.getColumns().addAll(columnaMatricula, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno);
        tablaAlumnos.setItems(coleccionInscripciones);
    }

    private void cargarDetallesGrupo() {
        txtClave.setDisable(true);
        txtNombre.setDisable(true);
        txtPeriodoEscolar.setDisable(true);
        txtPlanEstudio.setDisable(true);
        txtGrado.setDisable(true);
        tablaClases.setDisable(true);
        btnAgregarAlumno.setDisable(true);
        tablaAlumnos.setDisable(true);

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection transaccion = conexion.beginTransaction()) {
            cargarDatosGrupo(transaccion);
            cargarDatosClases(transaccion);
            cargarDatosAlumnos(transaccion);
        } catch (RecursoNoEncontradoException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                mensaje.setTitle("Recurso no encontrado");
                mensaje.showAndWait();
            });
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Error de base de datos", ButtonType.OK);
                mensaje.setTitle("Error de conexión");
                mensaje.showAndWait();
                e.printStackTrace();
            });
        } finally {
            txtClave.setDisable(false);
            txtNombre.setDisable(false);
            txtPeriodoEscolar.setDisable(false);
            txtPlanEstudio.setDisable(false);
            txtGrado.setDisable(false);
            tablaClases.setDisable(false);
            btnAgregarAlumno.setDisable(false);
            tablaAlumnos.setDisable(false);
        }
    }

    private void cargarDatosGrupo(Connection conexion) throws RecursoNoEncontradoException {
        //Repositorios
        var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
        var gradoRepositorio = new MySql2oGradoRepositorio(conexion);
        var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

        //Servicios
        var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
        var buscadorGrudo = new BuscadorGrado(gradoRepositorio);
        var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

        BuscarGrupos buscarGrupos = new BuscarGrupos(buscadorGrupo, buscadorGrudo, buscadorPeriodoEscolar);
        GrupoData grupo = buscarGrupos.buscarPorId(idGrupo);

        //cargarDatos en pantalla
        txtClave.setText(grupo.clave());
        txtNombre.setText(grupo.nombre());
        txtPeriodoEscolar.setText(grupo.periodoEscolar().nombre());
        //txtPlanEstudio.setText(grupo);
        txtGrado.setText(grupo.grado().nombre());
    }

    private void cargarDatosClases(Connection conexion) {
        colleccionClases.clear();

        //Repositorios
        var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
        var claseRepositorio = new MySql2oClaseRepositorio(conexion);
        var materiaRepositorio = new MySql2oMateriaRepositorio(conexion);
        var maestroRepositorio = new MySql2oMaestroRepositorio(conexion);

        //Servicios
        var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
        var buscadorClase = new BuscadorClase(claseRepositorio);
        var buscadorMateria = new BuscadorMateria(materiaRepositorio);
        var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);

        BuscarClasesPorGrupo buscarClasesPorGrupo = new BuscarClasesPorGrupo(buscadorGrupo, buscadorClase,
                buscadorMateria, buscadorMaestro);
        try {
            ClasesGrupoData clases = buscarClasesPorGrupo.buscar(idGrupo);
            colleccionClases.addAll(clases.clases());
        } catch (RecursoNoEncontradoException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                mensaje.setTitle("Recurso no encontrado");
                mensaje.showAndWait();
            });
        }
    }

    private void cargarDatosAlumnos(Connection conexion) {
        coleccionInscripciones.clear();

        //Repositorios
        var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
        var inscripcionRepositorio = new MySql2oInscripcionRepositorio(conexion);
        var alumnoRepositorio = new MySql2oAlumnoRepositorio(conexion);

        //Servicios
        var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
        var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
        var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);

        BuscarInscripcionesPorGrupo buscarInscripcionesPorGrupo = new BuscarInscripcionesPorGrupo(buscadorGrupo,
                buscadorInscripcion, buscadorAlumno);

        try {
            InscripcionesGrupoData inscripciones = buscarInscripcionesPorGrupo.buscar(this.idGrupo);
            coleccionInscripciones.addAll(inscripciones.inscripciones());
        } catch (RecursoNoEncontradoException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                mensaje.setTitle("Recurso no encontrado");
                mensaje.showAndWait();
            });
        }
    }

    private void asignarMaestro(ClaseGrupoData clase) {

    }


    private void removerAlumno(InscripcionGrupoData inscripcion) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas remover al alumno?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
            try (Connection transaccion = conexion.beginTransaction()) {
                //Repositorios
                var repositorioGrupo = new MySql2oGrupoRepositorio(transaccion);
                var repositorioInscripcion = new MySql2oInscripcionRepositorio(transaccion);

                //Servicios
                var buscadorGrupo = new BuscadorGrupo(repositorioGrupo);
                var buscadorInscripcion = new BuscadorInscripcion(repositorioInscripcion);
                var actualizadorGrupo = new ActualizadorGrupo(repositorioGrupo);

                //Eliminar inscripción
                GestionarInscripcionesGrupo gestionarInscripcionesGrupo = new GestionarInscripcionesGrupo(buscadorGrupo,
                        buscadorInscripcion, actualizadorGrupo);
                gestionarInscripcionesGrupo.removerInscripcion(this.idGrupo, inscripcion.id());

            } catch (RecursoNoEncontradoException e) {
                Alert mensaje = new Alert(Alert.AlertType.ERROR);
                mensaje.setTitle("Error");
                mensaje.setHeaderText("No se pudo remover al alumno");
                mensaje.setContentText(e.getMessage());
                mensaje.showAndWait();
            } catch (Sql2oException e) {
                e.printStackTrace();
                Alert mensaje = new Alert(Alert.AlertType.ERROR);
                mensaje.setTitle("Error");
                mensaje.setHeaderText("No se pudo remover al alumno");
                mensaje.setContentText("Ocurrió un error al intentar remover al alumno del grupo.");
                mensaje.showAndWait();
            }
        }
        temporizador.reiniciar();
    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos de detalles de grupo");
        if (temporizador != null)
            temporizador.cancel();
    }
}
