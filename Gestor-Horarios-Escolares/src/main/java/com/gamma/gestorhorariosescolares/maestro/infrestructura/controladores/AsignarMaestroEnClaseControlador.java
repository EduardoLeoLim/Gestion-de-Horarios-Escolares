package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.AsignarMaestro;
import com.gamma.gestorhorariosescolares.clase.aplicacion.actualizar.ActualizadorClase;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.AsignacionInvalidaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.DetallesGrupoControlador;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.horario.aplicacion.buscar.BuscadorHorario;
import com.gamma.gestorhorariosescolares.horario.infrestructura.persistencia.MySql2oHorarioRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.BuscarMaestros;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestrosData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
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

public class AsignarMaestroEnClaseControlador {

    private final Stage stage;
    private final Integer idClase;
    private final Integer idGrupo;
    private Temporizador temporizadorBusqueda;
    private ObservableList<MaestroData> coleccionMaestros;
    private boolean esBusquedaMaestro;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<MaestroData> tablaMaestros;

    public AsignarMaestroEnClaseControlador(Stage stage, Integer idGrupo, Integer idClase) {
        if (stage == null)
            throw new IllegalArgumentException("El stage no puede ser nulo");
        if (idClase == null)
            throw new IllegalArgumentException("El id de la clase no puede ser nulo");
        if (idGrupo == null)
            throw new IllegalArgumentException("El id del grupo no puede ser nulo");

        this.stage = stage;
        this.idClase = idClase;
        this.idGrupo = idGrupo;
        esBusquedaMaestro = true;

        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    private void initialize() {
        stage.setTitle("Asignar maestro");
        btnRegresar.setOnAction(event -> regresarDetalles());

        temporizadorBusqueda = new Temporizador(1, temporizador -> {
            tablaMaestros.setDisable(true);
            buscarMaestros(txtBuscar.getText().trim());
            tablaMaestros.setDisable(false);
        });

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()) || !esBusquedaMaestro)
                return;
            temporizadorBusqueda.reiniciar();
        });

        inicializarTablaMaestros();
        buscarMaestros();
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

    private void inicializarTablaMaestros() {
        coleccionMaestros = FXCollections.observableArrayList();

        TableColumn<MaestroData, String> columnaNoPersonal = new TableColumn<>("No. Personal");
        columnaNoPersonal.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().noPersonal()));
        columnaNoPersonal.setMinWidth(150);

        TableColumn<MaestroData, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().nombre()));
        columnaNombre.setMinWidth(150);

        TableColumn<MaestroData, String> columnaApellidoPaterno = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoPaterno()));
        columnaApellidoPaterno.setMinWidth(150);

        TableColumn<MaestroData, String> columnaApellidoMaterno = new TableColumn<>("Apellido Materno");
        columnaApellidoMaterno.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().apellidoMaterno()));
        columnaApellidoMaterno.setMinWidth(150);

        TableColumn<MaestroData, String> columnaCorreoElectronico = new TableColumn<>("Correo Electrónico");
        columnaCorreoElectronico.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().usuario().correoElectronico()));
        columnaCorreoElectronico.setMinWidth(200);

        TableColumn<MaestroData, String> columnaAsignar = new TableColumn<>();
        columnaAsignar.setMinWidth(100);
        columnaAsignar.setMaxWidth(100);
        columnaAsignar.setSortable(false);
        columnaAsignar.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(null);
                setText(null);
                if (empty)
                    return;

                MaestroData maestro = getTableView().getItems().get(getIndex());

                Button btnAsignar = new Button("Asignar");
                btnAsignar.getStyleClass().addAll("b", "btn-primary");
                btnAsignar.setOnAction(event -> asignarMaestro(maestro));
                setGraphic(btnAsignar);
            }
        });

        tablaMaestros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMaestros.getColumns().addAll(columnaNoPersonal, columnaNombre, columnaApellidoPaterno, columnaApellidoMaterno,
                columnaCorreoElectronico, columnaAsignar);
        tablaMaestros.setItems(coleccionMaestros);
    }

    private void asignarMaestro(MaestroData maestro) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var claseRepositorio = new MySql2oClaseRepositorio(transaccion);
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);
            var grupoRepositorio = new MySql2oGrupoRepositorio(transaccion);
            var horarioRepositorio = new MySql2oHorarioRepositorio(transaccion);

            //Servicios
            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorHorario = new BuscadorHorario(horarioRepositorio);
            var actualizadorClase = new ActualizadorClase(claseRepositorio);


            AsignarMaestro asignarMaestro = new AsignarMaestro(buscadorClase, buscadorMaestro, buscadorGrupo,
                    buscadorHorario, actualizadorClase);

            asignarMaestro.asignar(idGrupo, maestro.id());

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "El maestro ha sido asignado al grupo.",
                    ButtonType.OK);
            mensaje.setTitle("Asignar Maestro");
            mensaje.setHeaderText("Maestro asignado");
            mensaje.showAndWait();

            regresarDetalles();

        } catch (RecursoNoEncontradoException | AsignacionInvalidaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error");
            mensaje.setHeaderText("Error al asignar maestro");
            mensaje.setContentText(e.getMessage());
            mensaje.showAndWait();

            buscarMaestros();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error de conexión");
            mensaje.setHeaderText("No se pudo establecer conexión con la base de datos");
            mensaje.setContentText("No se pudo asignar el maestro a la clase");
            mensaje.showAndWait();

            buscarMaestros();
        }
    }

    private void buscarMaestros() {
        esBusquedaMaestro = false;
        txtBuscar.setText("");
        esBusquedaMaestro = true;
        temporizadorBusqueda.reiniciar();
    }

    private void buscarMaestros(String criterioBusqueda) {
        if (criterioBusqueda == null)
            criterioBusqueda = "";

        MaestrosData maestros;
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            BuscarMaestros buscarMaestros = new BuscarMaestros(buscadorMaestro, buscadorUsuario);

            if (criterioBusqueda.isBlank()) {
                maestros = buscarMaestros.buscarHabilidatos();
            } else {
                System.out.println("Busqueda de maestros por criterio: '" + criterioBusqueda + "'");
                maestros = buscarMaestros.buscarPorCriterio(criterioBusqueda);
            }

            cargarMaestrosEnTabla(maestros);
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
                mensaje.setTitle("Error de base de datos");
                mensaje.showAndWait();
            });
        }
    }

    private void cargarMaestrosEnTabla(MaestrosData maestros) {
        if (maestros == null)
            return;

        coleccionMaestros.clear();
        coleccionMaestros.addAll(maestros.maestros());
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
