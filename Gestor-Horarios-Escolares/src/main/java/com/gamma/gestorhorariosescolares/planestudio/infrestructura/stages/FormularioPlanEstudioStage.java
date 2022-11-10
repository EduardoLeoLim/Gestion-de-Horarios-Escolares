package com.gamma.gestorhorariosescolares.planestudio.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.salon.infrestructura.controladores.FormularioPlanEstudioControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioPlanEstudioStage extends CustomStage {

    private AnchorPane panelFormulario;
    private FormularioPlanEstudioControlador controladorFormulario;

    public FormularioPlanEstudioStage(){
        setTitle("Registrar Plan de Estudio");
        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        controladorFormulario = new FormularioPlanEstudioControlador(this);

        try{
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "planEstudio/infrestructura/vistas/FormularioPlanEstudio.fxml",
                    controladorFormulario);
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
