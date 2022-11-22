package com.gamma.gestorhorariosescolares.inscripcion.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GestionarInscripcionesGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.actualizar.ActualizadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.DetallesGrupoControlador;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.BuscarInscripcionesSinAsignar;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.InscripcionGrupoData;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.InscripcionesGrupoData;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar.BuscadorInscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia.MySql2oInscripcionRepositorio;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.io.IOException;
import java.time.LocalDate;

public class AsignarInscripcionEnGrupoControlador {

    private final Stage stage;
    private final Integer idGrupo;
    private Temporizador temporizadorBusqueda;
    private ObservableList<InscripcionGrupoData> coleccionInscripciones;


    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<InscripcionGrupoData> tablaInscripciones;
    private boolean esBusquedaInscripciones;

    public AsignarInscripcionEnGrupoControlador(Stage stage, Integer idGrupo) {
        this.stage = stage;
        this.idGrupo = idGrupo;
        esBusquedaInscripciones = true;

        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    private void initialize() {
        stage.setTitle("Asignar alumno");
        btnRegresar.setOnAction(event -> regresarDetalles());

        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            //Búsqueda de inscripciones
            tablaInscripciones.setDisable(true);
            buscarInscripciones(txtBuscar.getText().trim());
            tablaInscripciones.setDisable(false);
        });

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaInscripciones)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicilizarTablaInscripciones();
        buscarInscripciones();
    }

    private void regresarDetalles() {
        try {
            DetallesGrupoControlador controlador = new DetallesGrupoControlador(stage, idGrupo);
            AnchorPane panel = InicializarPanel.inicializarAnchorPane("grupo/infrestructura/vistas/DetallesGrupo.fxml",
                    controlador);
            panel.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
            ((CustomStage) stage).setContent(panel);
            liberarRecursos();
        } catch (IOException e) {
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }

    private void inicilizarTablaInscripciones() {
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

        TableColumn<InscripcionGrupoData, String> columnaFechaInscripcion = new TableColumn<>("Fecha de inscripción");
        columnaFechaInscripcion.setMinWidth(150);
        columnaFechaInscripcion.setCellValueFactory(ft -> {
            LocalDate fechaRegistro = ft.getValue().fechaRegistro();
            return new SimpleStringProperty(fechaRegistro.getDayOfMonth() + "/" + fechaRegistro.getMonthValue() + "/" + fechaRegistro.getYear());
        });

        TableColumn<InscripcionGrupoData, String> columnaAsignar = new TableColumn<>();
        columnaAsignar.setMaxWidth(100);
        columnaAsignar.setMinWidth(100);
        columnaAsignar.setCellFactory(ft -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (empty)
                    return;

                InscripcionGrupoData inscripcion = getTableView().getItems().get(getIndex());

                Button btnAsignar = new Button("Asignar");
                btnAsignar.getStyleClass().addAll("b", "btn-primary");
                btnAsignar.setOnAction(event -> asignarInscripcion(inscripcion));
                setGraphic(btnAsignar);
            }
        });


        tablaInscripciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaInscripciones.getColumns().addAll(columnaMatricula, columnaNombre, columnaApellidoPaterno,
                columnaApellidoMaterno, columnaFechaInscripcion, columnaAsignar);
        tablaInscripciones.setItems(coleccionInscripciones);
    }

    private void asignarInscripcion(InscripcionGrupoData inscripcion) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection trasaccion = conexion.beginTransaction()) {
            //Repositorios
            var grupoRepositorio = new MySql2oGrupoRepositorio(trasaccion);
            var inscripcionRepositorio = new MySql2oInscripcionRepositorio(trasaccion);

            //Servicios
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
            var actualizadorGrupo = new ActualizadorGrupo(grupoRepositorio);

            GestionarInscripcionesGrupo gestionarInscripcionesGrupo = new GestionarInscripcionesGrupo(buscadorGrupo,
                    buscadorInscripcion, actualizadorGrupo);

            gestionarInscripcionesGrupo.agregarInscripcion(this.idGrupo, inscripcion.id());

            trasaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Inscripción asignada");
            mensaje.setHeaderText("La inscripción se ha asignado correctamente.");
            mensaje.showAndWait();

            regresarDetalles();

        } catch (RecursoNoEncontradoException | Sql2oException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al asignar inscripción");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            buscarInscripciones();
        }
    }

    private void buscarInscripciones() {
        esBusquedaInscripciones = false;
        txtBuscar.setText("");
        esBusquedaInscripciones = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarInscripciones(String criterioBusqueda) {
        coleccionInscripciones.clear();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var grupoRepositorio = new MySql2oGrupoRepositorio(transaccion);
            var inscripcionRepositorio = new MySql2oInscripcionRepositorio(transaccion);
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(transaccion);

            //Servicios
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);

            BuscarInscripcionesSinAsignar buscarInscripcionesSinAsignar = new BuscarInscripcionesSinAsignar(buscadorGrupo,
                    buscadorInscripcion, buscadorAlumno);

            InscripcionesGrupoData inscripciones;
            if (criterioBusqueda.isBlank())
                inscripciones = buscarInscripcionesSinAsignar.buscar(idGrupo);
            else
                inscripciones = buscarInscripcionesSinAsignar.buscar(idGrupo, criterioBusqueda);

            cargarInscripcionesEnTabla(inscripciones);

        } catch (RecursoNoEncontradoException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                mensaje.setTitle("Error al buscar inscripciones");
                mensaje.showAndWait();
            });
        } catch (Sql2oException ex) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
                mensaje.setTitle("Error de base de datos");
                mensaje.showAndWait();
            });
        }
    }

    private void cargarInscripcionesEnTabla(InscripcionesGrupoData inscripciones) {
        coleccionInscripciones.clear();

        if (inscripciones == null)
            return;

        coleccionInscripciones.addAll(inscripciones.inscripciones());
    }

    public void liberarRecursos() {
        System.out.println("Liberando recursos de la ventana de asignar inscripciones");

        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}