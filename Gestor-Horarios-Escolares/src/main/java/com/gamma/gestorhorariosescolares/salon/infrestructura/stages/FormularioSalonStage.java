package com.gamma.gestorhorariosescolares.salon.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores.FormularioEdificioControlador;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.infrestructura.controladores.FormularioSalonControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioSalonStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private SalonData salonEdicion;
    AnchorPane panelFormulario;
    FormularioSalonControlador controladorFormulario;

    public FormularioSalonStage() {
        setTitle("Registrar salón");
        this.esNuevoRegistro = true;

        cargarFormulario();
    }

    public FormularioSalonStage(SalonData salonEdicion) {
        if (salonEdicion == null)
            throw new NullPointerException();
        setTitle("Editar salón");
        this.salonEdicion = salonEdicion;
        this.esNuevoRegistro = false;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioSalonControlador(this);
        else
            controladorFormulario = new FormularioSalonControlador(this, salonEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "salon/infrestructura/vistas/FormularioSalon.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}