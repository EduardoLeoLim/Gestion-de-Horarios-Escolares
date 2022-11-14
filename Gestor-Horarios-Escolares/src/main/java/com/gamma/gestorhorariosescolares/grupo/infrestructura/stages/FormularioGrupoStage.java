package com.gamma.gestorhorariosescolares.grupo.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.FormularioGrupoControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioGrupoStage extends CustomStage {

    private AnchorPane panelFormulario;
    private FormularioGrupoControlador controladorFormulario;

    public FormularioGrupoStage() {
        setTitle("Registrar grupo");

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        controladorFormulario = new FormularioGrupoControlador(this);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "grupo/infrestructura/vistas/FormularioGrupo.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
