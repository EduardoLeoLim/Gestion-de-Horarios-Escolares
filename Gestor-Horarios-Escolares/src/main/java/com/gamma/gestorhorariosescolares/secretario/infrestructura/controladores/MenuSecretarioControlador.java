package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.infrestructura.controladores.HorariosDisponiblesControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.CatalogoGruposControlador;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MenuSecretarioControlador {

    private final Stage stage;
    private AnchorPane panelCatalogoGrupos;
    private CatalogoGruposControlador controladorGrupos;
    private AnchorPane panelHorariosDisponibles;
    private HorariosDisponiblesControlador controladorHorariosDisponibles;

    @FXML
    private BorderPane panelMenuSecretario;
    @FXML
    private VBox vbMenu;
    @FXML
    private Button btnCatalogoGrupos;
    @FXML
    private Button btnCatalogoInscripciones;
    @FXML
    private Button btnConsultarHorarios;
    @FXML
    private Button btnRegistrarClase;
    @FXML
    private Button btnCerrarSesion;

    public MenuSecretarioControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Solo se puede acceder a los recursos @FXML aquí
        mostrarCatalogoGruposClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarCatalogoGruposClick() {
        if (panelCatalogoGrupos == null) {
            try {
                controladorGrupos = new CatalogoGruposControlador(stage);
                panelCatalogoGrupos = InicializarPanel.inicializarAnchorPane(
                        "grupo/infrestructura/vistas/CatalogoGrupos.fxml",
                        controladorGrupos
                );
                panelCatalogoGrupos.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuSecretario.setCenter(panelCatalogoGrupos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuSecretario.setCenter(panelCatalogoGrupos);
        }
    }

    @FXML
    protected void mostrarCatalogoInscripcionesClick() {

    }

    @FXML
    protected void mostrarConsultarHorariosClick() {

    }

    @FXML
    protected void mostrarRegistrarClaseClick() {
        if (panelHorariosDisponibles == null) {
            try {
                controladorHorariosDisponibles = new HorariosDisponiblesControlador(stage);
                panelHorariosDisponibles = InicializarPanel.inicializarAnchorPane(
                        "clase/infrestructura/vistas/HorariosDisponibles.fxml",
                        controladorHorariosDisponibles
                );
                panelHorariosDisponibles.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuSecretario.setCenter(panelHorariosDisponibles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelMenuSecretario.setCenter(panelHorariosDisponibles);
        }
    }

    @FXML
    protected void cerrarSesionClick() {
        new LoginStage().show();
        stage.close();
    }

    private void liberarRecursos() {
        System.out.println("Liberando recursos");
        if (controladorGrupos != null)
            controladorGrupos.liberarRecursos();
    }

}