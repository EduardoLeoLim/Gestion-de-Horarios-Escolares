package com.gamma.gestorhorariosescolares.materia.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.infrestructura.controladores.FormularioMateriaControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioMateriaStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private AnchorPane panelFormulario;
    private FormularioMateriaControlador controladorFormulario;
    private MateriaData materiaEdicion;

    public FormularioMateriaStage() {
        setTitle("Registrar materia");
        this.esNuevoRegistro = true;

        cargarFormulario();
    }

    public FormularioMateriaStage(MateriaData materiaEdicion) {
        if (materiaEdicion == null)
            throw new NullPointerException();
        setTitle("Editar materia");
        this.materiaEdicion = materiaEdicion;
        this.esNuevoRegistro = false;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioMateriaControlador(this);
        else
            controladorFormulario = new FormularioMateriaControlador(this, materiaEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "materia/infrestructura/vistas/FormularioMateria.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
