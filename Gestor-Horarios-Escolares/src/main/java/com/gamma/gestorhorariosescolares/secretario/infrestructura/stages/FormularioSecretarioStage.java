package com.gamma.gestorhorariosescolares.secretario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretarioData;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores.FormularioSecretarioControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioSecretarioStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private SecretarioData secretarioEdicion;
    private AnchorPane panelFormulario;
    private FormularioSecretarioControlador controladorFormulario;

    public FormularioSecretarioStage() {
        setTitle("Registrar Secretario");
        esNuevoRegistro = true;

        setMinWidth(400);
        setMinHeight(500);
        setWidth(400);
        setHeight(500);
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        cargarFormulario();
    }

    public FormularioSecretarioStage(SecretarioData secretarioEdicion) {
        if (secretarioEdicion == null)
            throw new NullPointerException();
        setTitle("Editar Administrador");
        this.secretarioEdicion = secretarioEdicion;
        esNuevoRegistro = false;

        setMinWidth(400);
        setMinHeight(500);
        setWidth(400);
        setHeight(500);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        cargarFormulario();
    }

    private void cargarFormulario() {
        if (esNuevoRegistro)
            controladorFormulario = new FormularioSecretarioControlador(this);
        else
            controladorFormulario = new FormularioSecretarioControlador(this, secretarioEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "secretario/infrestructura/vistas/FormularioSecretario.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }
}
