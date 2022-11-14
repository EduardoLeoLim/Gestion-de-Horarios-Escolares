package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores.CatalogoAdministradoresControlador;
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

public class MenuAlumnoControlador {

    private final Stage stage;
    private AnchorPane panelHorarioAlumno;
    private HorarioAlumnoControlador controladorHorarioAlumno;
    private AnchorPane panelMateriasAlumno;
    private MateriasAlumnoControlador controladorMateriasAlumno;
    private AnchorPane panelCalificacionesAlumno;
    private CalificacionesAlumnoControlador controladorCalificacionesAlumno;

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


    public MenuAlumnoControlador(Stage stage){
        this.stage = stage;

    }

    public void initialize(){
        mostrarHorarioClick();
        stage.setOnHiding(event -> liberarRecursos());
    }

    @FXML
    protected void mostrarHorarioClick(){
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
    protected void mostrarMateriasClick(){
        if (panelMateriasAlumno == null){
            try{
                controladorMateriasAlumno = new MateriasAlumnoControlador(stage);
                panelMateriasAlumno = InicializarPanel.inicializarAnchorPane(
                        "alumno/infrestructura/vistas/MateriasAlumno.fxml",
                        controladorMateriasAlumno
                );
                panelMateriasAlumno.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAlumno.setCenter(panelMateriasAlumno);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            panelMenuAlumno.setCenter(panelMateriasAlumno);
        }
    }

    @FXML
    protected void mostrarCalificacionesClick(){
        if (panelCalificacionesAlumno == null){
            try{
                controladorCalificacionesAlumno = new CalificacionesAlumnoControlador(stage);
                panelCalificacionesAlumno = InicializarPanel.inicializarAnchorPane(
                        "alumno/infrestructura/vistas/CalificacionesAlumno.fxml",
                        controladorCalificacionesAlumno
                );
                panelCalificacionesAlumno.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                panelMenuAlumno.setCenter(panelCalificacionesAlumno);

            }catch(IOException e){
                e.printStackTrace();
            }
        }else {
            panelMenuAlumno.setCenter(panelCalificacionesAlumno);

        }
    }

    @FXML
    protected void cerrarSesionClick() {
        new LoginStage().show();
        stage.close();
    }

    private void liberarRecursos(){
        System.out.println("Liberando recursos");

        if (controladorHorarioAlumno != null)
            controladorHorarioAlumno.liberarRecursos();
        if (controladorMateriasAlumno != null)
            controladorMateriasAlumno.liberarRecursos();
        if (controladorCalificacionesAlumno != null)
            controladorCalificacionesAlumno.liberarRecursos();
    }
}
