package com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.controladores.FormularioPeriodoEscolarControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioPeriodoEscolarStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private FormularioPeriodoEscolarControlador controladorFormulario;
    private PeriodoEscolarData periodoEscolarEdicion;
    private AnchorPane panelFormulario;

    public FormularioPeriodoEscolarStage() {
        setTitle("Registrar Periodo Escolar");
        esNuevoRegistro = true;

        cargarFormulario();
    }

    public FormularioPeriodoEscolarStage(PeriodoEscolarData periodoEscolarEdicion) {
        if (periodoEscolarEdicion == null)
            throw new NullPointerException();

        setTitle("Actualizar Periodo Escolar");
        this.periodoEscolarEdicion = periodoEscolarEdicion;
        this.esNuevoRegistro = false;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioPeriodoEscolarControlador(this);
        else
            controladorFormulario = new FormularioPeriodoEscolarControlador(this, periodoEscolarEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "periodoescolar/infrestructura/vistas/FormularioPeriodoEscolar.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
