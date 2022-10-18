package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores.CatalogoAlumnosControlador;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores.CatalogoMaestrosControlador;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores.CatalogoSecretariosControlador;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MenuAdministradorControlador {
    private final Stage stage;

    private AnchorPane panelCatalogoAdministradores;

    private CatalogoAdministradoresControlador controladorAdministradores;

    private AnchorPane panelCatalogoSecretarios;

    private CatalogoSecretariosControlador controladorSecretarios;

    private AnchorPane panelCatalogoMaestros;

    private CatalogoMaestrosControlador controladorMaestros;

    private AnchorPane panelCatalogoAlumnos;

    private CatalogoAlumnosControlador controladorAlumnos;

    @FXML
    private BorderPane panelMenuAdministrador;

    @FXML
    private VBox vbMenu;

    @FXML
    private Button btnCatalogoAdministradores;

    @FXML
    private Button bbtnCatalogoSecretarios;

    @FXML
    private Button btnCatalogoMaestros;

    @FXML
    private Button btnCatalogoAlumnos;

    @FXML
    private Button btnCatalogoEdificios;

    @FXML
    private Button btnCatalogoSalones;

    @FXML
    private Button btnCatalogoMaterias;

    @FXML
    private Button btnCatalogoPlanesEstudios;

    @FXML
    private Button btnCatalogoPeriodosEscolares;

    @FXML
    private Button btnCerrarSesion;

    public MenuAdministradorControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Solo se puede acceder a los recursos @FXML aquí
        vbMenu.getStylesheets().add(App.class.getResource("compartido/infrestructura/estilos/Menu.css").toExternalForm());
        mostrarCatalogoAdministradoresClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarCatalogoAdministradoresClick() {
        if (panelCatalogoAdministradores == null) {
            try {
                FXMLLoader rootAdministradores = new FXMLLoader(App.class.getResource("administrador/infrestructura/vistas/CatalogoAdministradores.fxml"));
                controladorAdministradores = new CatalogoAdministradoresControlador(stage);
                rootAdministradores.setController(controladorAdministradores);
                rootAdministradores.load();
                panelCatalogoAdministradores = rootAdministradores.getRoot();
                panelCatalogoAdministradores.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAdministrador.setCenter(panelCatalogoAdministradores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAdministrador.setCenter(panelCatalogoAdministradores);
        }
    }

    @FXML
    protected void mostrarCatalogoSecretariosClick() {
        if (panelCatalogoSecretarios == null) {
            try {
                FXMLLoader rootSecretarios = new FXMLLoader(App.class.getResource("secretario/infrestructura/vistas/CatalogoSecretarios.fxml"));
                controladorSecretarios = new CatalogoSecretariosControlador(stage);
                rootSecretarios.setController(controladorSecretarios);
                rootSecretarios.load();
                panelCatalogoSecretarios = rootSecretarios.getRoot();
                panelCatalogoSecretarios.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAdministrador.setCenter(panelCatalogoSecretarios);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAdministrador.setCenter(panelCatalogoSecretarios);
        }
    }

    @FXML
    protected void mostrarCatalogoMaestrosClick() {
        if (panelCatalogoMaestros == null) {
            try {
                FXMLLoader rootMaestros = new FXMLLoader(App.class.getResource("maestro/infrestructura/vistas/CatalogoMaestros.fxml"));
                controladorMaestros = new CatalogoMaestrosControlador(stage);
                rootMaestros.setController(controladorMaestros);
                rootMaestros.load();
                panelCatalogoMaestros = rootMaestros.getRoot();
                panelCatalogoMaestros.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAdministrador.setCenter(panelCatalogoMaestros);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuAdministrador.setCenter(panelCatalogoMaestros);
        }
    }

    @FXML
    protected void mostrarCatalogoAlumnosClick() {
        if (panelCatalogoAlumnos == null) {
            try {
                FXMLLoader rootAlumnos = new FXMLLoader(App.class.getResource("alumno/infrestructura/vistas/CatalogoAlumnos.fxml"));
                controladorAlumnos = new CatalogoAlumnosControlador(stage);
                rootAlumnos.setController(controladorAlumnos);
                rootAlumnos.load();
                panelCatalogoAlumnos = rootAlumnos.getRoot();
                panelCatalogoAlumnos.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAdministrador.setCenter(panelCatalogoAlumnos);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            panelMenuAdministrador.setCenter(panelCatalogoAlumnos);
        }
    }

    @FXML
    protected void mostrarCatalogoEdificiosClick() {

    }

    @FXML
    protected void mostrarCatalogoSalonesClick() {

    }

    @FXML
    protected void mostrarCatalogoPlanesEstudioClick() {

    }

    @FXML
    protected void mostrarCatalogoMateriasClick() {

    }

    @FXML
    protected void mostrarCatalogoPeriodosEscolaresClick() {

    }

    @FXML
    protected void cerrarSesionClick() {
        new LoginStage().show();
        stage.close();
    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos");

        if (controladorAdministradores != null)
            controladorAdministradores.liberarRecursos();
        if (controladorSecretarios != null)
            controladorSecretarios.liberarRecursos();
        if (controladorMaestros != null)
            controladorMaestros.liberarRecursos();
        if (controladorAlumnos != null)
            controladorAlumnos.liberarRecursos();
    }
}
