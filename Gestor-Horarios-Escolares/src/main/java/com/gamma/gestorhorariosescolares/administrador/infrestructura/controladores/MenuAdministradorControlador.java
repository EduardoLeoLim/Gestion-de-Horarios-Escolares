package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    }

    @FXML
    protected void mostrarCatalogoAdministradoresClick() {
        if (panelCatalogoAdministradores == null){
            try{
                FXMLLoader rootAdministradores = new FXMLLoader(App.class.getResource("administrador/infrestructura/vistas/CatalogoAdministradores.fxml"));
                var administradoresControlador = new CatalogoAdministradoresControlador(stage);
                rootAdministradores.setController(administradoresControlador);
                rootAdministradores.load();
                panelCatalogoAdministradores = rootAdministradores.getRoot();
                panelCatalogoAdministradores.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

                panelMenuAdministrador.setCenter(panelCatalogoAdministradores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        panelMenuAdministrador.setCenter(panelCatalogoAdministradores);
    }

    @FXML
    protected void mostrarCatalogoSecretariosClick() {

    }

    @FXML
    protected void mostrarCatalogoMaestrosClick() {

    }

    @FXML
    protected void mostrarCatalogoAlumnosClick() {

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
}
