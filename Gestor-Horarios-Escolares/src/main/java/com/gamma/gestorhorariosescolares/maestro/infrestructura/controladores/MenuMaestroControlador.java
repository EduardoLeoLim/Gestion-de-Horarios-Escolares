package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.BuscarAlumnos;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.BuscarMaestros;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
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

public class MenuMaestroControlador {

    private final Stage stage;
    private AnchorPane panelHorarioMaestro;
    private HorarioMaestroControlador controladorHorarioMaestro;
    private AnchorPane panelMateriasMaestro;
    private MateriasMaestroControlador controladorMateriasMaestro;
    private MaestroData maestroConectado;

    @FXML
    private BorderPane panelMenuMaestro;
    @FXML
    private VBox vbMenu;
    @FXML
    private Button btnConsultarHorario;
    @FXML
    private Button btnConsultarMateriasImpartidas;
    @FXML
    private Button btnCerrarSesion;

    public MenuMaestroControlador(Stage stage, UsuarioData usuario) {
        this.stage = stage;
        this.maestroConectado = buscarMaestroConectado(usuario);
        if (maestroConectado == null){
            throw new NullPointerException("Maestro no encontrado");
        }
        if (stage == null){
            throw new NullPointerException("Error al cargar el menu");
        }
    }

    @FXML
    public void initialize(){
        mostrarHorarioClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarHorarioClick(){
        if (panelHorarioMaestro == null){
            try{
                controladorHorarioMaestro = new HorarioMaestroControlador(stage);
                panelHorarioMaestro = InicializarPanel.inicializarAnchorPane(
                        "maestro/infrestructura/vistas/HorarioMaestro.fxml",
                        controladorHorarioMaestro);
                panelHorarioMaestro.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuMaestro.setCenter(panelHorarioMaestro);

            } catch (IOException e){
                e.printStackTrace();
            }
        }else {
            panelMenuMaestro.setCenter(panelHorarioMaestro);
        }
    }

    @FXML
    protected void mostrarMateriasImpartidasClick(){
        if (panelMateriasMaestro == null){
            try{
                controladorMateriasMaestro = new MateriasMaestroControlador(stage, this.maestroConectado);
                panelMateriasMaestro = InicializarPanel.inicializarAnchorPane(
                        "maestro/infrestructura/vistas/MateriasMaestro.fxml",
                        controladorMateriasMaestro);
                panelMateriasMaestro.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuMaestro.setCenter(panelMateriasMaestro);

            } catch (IOException e){
                e.printStackTrace();
            }
        }else {
            panelMenuMaestro.setCenter(panelMateriasMaestro);
        }
    }

    private MaestroData buscarMaestroConectado(UsuarioData usuario) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection trasaccion = conexion.beginTransaction()) {
            //Repositorios
            var maestroRepositorio = new MySql2oMaestroRepositorio(trasaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(trasaccion);

            //Servicios
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            BuscarMaestros buscarMaestros = new BuscarMaestros(buscadorMaestro, buscadorUsuario);
            return buscarMaestros.buscarPorUsuario(usuario.id());


        } catch (Sql2oException e) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();

        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("El maestro no ha sido encontrado");
            mensaje.showAndWait();


        }
        return null;

    }


    @FXML
    protected void cerrarSesionClick(){
        new LoginStage().show();
        stage.close();
    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos");

        if (controladorHorarioMaestro != null)
            controladorHorarioMaestro.liberarRecursos();
        if (controladorMateriasMaestro != null)
            controladorMateriasMaestro.liberarRecursos();

    }


}
