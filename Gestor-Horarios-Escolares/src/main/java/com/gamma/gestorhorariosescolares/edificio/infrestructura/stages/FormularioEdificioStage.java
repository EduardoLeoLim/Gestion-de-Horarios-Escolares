package com.gamma.gestorhorariosescolares.edificio.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores.FormularioEdificioControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioEdificioStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private EdificioData edificioEdicion;
    AnchorPane panelFormulario;
    FormularioEdificioControlador controladorFormulario;

    public FormularioEdificioStage() {
        setTitle("Registrar Edificio");
        esNuevoRegistro = true;

        cargarFormulario();
    }

    public FormularioEdificioStage(EdificioData edificioEdicion) {
        if (edificioEdicion == null)
            throw new NullPointerException();
        setTitle("Editar Edificio");
        this.edificioEdicion = edificioEdicion;
        this.esNuevoRegistro = false;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioEdificioControlador(this);
        else
            controladorFormulario = new FormularioEdificioControlador(this, edificioEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "edificio/infrestructura/vistas/FormularioEdificio.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
