package com.gamma.gestorhorariosescolares.maestro.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores.FormularioMaestroControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioMaestroStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private MaestroData maestroEdicion;
    private AnchorPane panelFormulario;
    FormularioMaestroControlador controladorFormulario;
    private boolean sePuedeMostrar;

    public FormularioMaestroStage() {
        setTitle("Registrar Maestro");
        esNuevoRegistro = true;
        sePuedeMostrar = true;

        cargarFormulario();
    }

    public FormularioMaestroStage(MaestroData maestroEdicion) {
        if (maestroEdicion == null)
            throw new NullPointerException();

        setTitle("Actualizar Maestro");
        this.maestroEdicion = maestroEdicion;
        esNuevoRegistro = false;
        sePuedeMostrar = true;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setMinWidth(400);
        setMinHeight(500);
        setWidth(400);
        setHeight(500);
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioMaestroControlador(this);
        else
            controladorFormulario = new FormularioMaestroControlador(this, maestroEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "maestro/infrestructura/vistas/FormularioMaestro.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
            sePuedeMostrar = false;
        }
    }

    @Override
    public void showAndWait() {
        if(sePuedeMostrar)
            super.showAndWait();
    }
}