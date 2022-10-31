package com.gamma.gestorhorariosescolares.alumno.infrestructura.stages;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores.FormularioAlumnoControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioAlumnoStage extends CustomStage {

    private final boolean esNuevoRegistro;
    private AlumnoData alumnoEdicion;
    private AnchorPane panelFormulario;
    private FormularioAlumnoControlador controladorFormulario;

    public FormularioAlumnoStage() {
        setTitle("Registrar Alumno");
        esNuevoRegistro = true;

        cargarFormulario();
    }

    public FormularioAlumnoStage(AlumnoData alumnoEdicion) {
        if (alumnoEdicion == null)
            throw new NullPointerException();
        setTitle("Editar Alumno");
        this.alumnoEdicion = alumnoEdicion;
        this.esNuevoRegistro = false;

        cargarFormulario();
    }

    private void cargarFormulario() {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        if (esNuevoRegistro)
            controladorFormulario = new FormularioAlumnoControlador(this);
        else
            controladorFormulario = new FormularioAlumnoControlador(this, alumnoEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "alumno/infrestructura/vistas/FormularioAlumno.fxml",
                    controladorFormulario
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
