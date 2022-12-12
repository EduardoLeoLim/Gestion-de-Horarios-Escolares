package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores.CatalogoAdministradoresControlador;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.BuscarAlumnos;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.io.IOException;

public class MenuAlumnoControlador {

    private final Stage stage;
    private AnchorPane panelHorarioAlumno;
    private HorarioAlumnoControlador controladorHorarioAlumno;
    private AnchorPane panelMateriasAlumno;
    private MateriasAlumnoControlador controladorMateriasAlumno;
    private AnchorPane panelCalificacionesAlumno;
    private CalificacionesAlumnoControlador controladorCalificacionesAlumno;

    private AlumnoData alumnoConectado;

    @FXML
    private BorderPane panelMenuAlumno;
    @FXML
    private VBox vbMenu;
    @FXML
    private Button btnConsultarHorario;
    @FXML
    private Button btnConsultarMaterias;
    @FXML
    private Button btnConsultarCalificaciones;
    @FXML
    private Button btnCerrarSesion;


    public MenuAlumnoControlador(Stage stage, UsuarioData usuario) {
        this.stage = stage;
        this.alumnoConectado = buscarAlumnoConectado(usuario);
        if (alumnoConectado == null){
            throw new NullPointerException("Alumno no encontrado");
        }
        if (stage == null){
            throw new NullPointerException("Error al cargar el menu");
        }


    }

    public void initialize() {
        mostrarHorarioClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarHorarioClick() {
        if (panelHorarioAlumno == null) {
            try {
                controladorHorarioAlumno = new HorarioAlumnoControlador(stage);
                panelHorarioAlumno = InicializarPanel.inicializarAnchorPane(
                        "alumno/infrestructura/vistas/HorarioAlumno.fxml",
                        controladorHorarioAlumno
                );
                panelHorarioAlumno.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAlumno.setCenter(panelHorarioAlumno);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAlumno.setCenter(panelHorarioAlumno);
        }

    }

    @FXML
    protected void mostrarMateriasClick() {
        if (panelMateriasAlumno == null) {
            try {
                controladorMateriasAlumno = new MateriasAlumnoControlador(stage, alumnoConectado);
                panelMateriasAlumno = InicializarPanel.inicializarAnchorPane(
                        "alumno/infrestructura/vistas/MateriasAlumno.fxml",
                        controladorMateriasAlumno
                );
                panelMateriasAlumno.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAlumno.setCenter(panelMateriasAlumno);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAlumno.setCenter(panelMateriasAlumno);
        }
    }

    @FXML
    protected void mostrarCalificacionesClick() {
        if (panelCalificacionesAlumno == null) {
            try {
                controladorCalificacionesAlumno = new CalificacionesAlumnoControlador(stage, alumnoConectado);
                panelCalificacionesAlumno = InicializarPanel.inicializarAnchorPane(
                        "alumno/infrestructura/vistas/CalificacionesAlumno.fxml",
                        controladorCalificacionesAlumno
                );
                panelCalificacionesAlumno.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAlumno.setCenter(panelCalificacionesAlumno);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAlumno.setCenter(panelCalificacionesAlumno);

        }
    }

    @FXML
    protected void cerrarSesionClick() {
        new LoginStage().show();
        stage.close();
    }

    private AlumnoData buscarAlumnoConectado(UsuarioData usuario) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection trasaccion = conexion.beginTransaction()) {
            //Repositorios
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(trasaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(trasaccion);

            //Servicios
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            BuscarAlumnos buscarAlumnos = new BuscarAlumnos(buscadorAlumno, buscadorUsuario);
            return buscarAlumnos.buscarPorUsuario(usuario.id());


        } catch (Sql2oException e) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();

        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("El alumno no ha sido encontrado");
            mensaje.showAndWait();


        }
        return null;

    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos");

        if (controladorHorarioAlumno != null)
            controladorHorarioAlumno.liberarRecursos();
        if (controladorMateriasAlumno != null)
            controladorMateriasAlumno.liberarRecursos();
        if (controladorCalificacionesAlumno != null)
            controladorCalificacionesAlumno.liberarRecursos();
    }
}
