package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.stages.LoginStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MenuMaestroControlador {

    private final Stage stage;
    private AnchorPane panelHorarioMaestro;
    private HorarioMaestroControlador controladorHorarioMaestro;
    private AnchorPane panelMateriasMaestro;
    private MateriasMaestroControlador controladorMateriasMaestro;

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

    public MenuMaestroControlador(Stage stage) {
        this.stage = stage;
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
                controladorMateriasMaestro = new MateriasMaestroControlador(stage);
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
