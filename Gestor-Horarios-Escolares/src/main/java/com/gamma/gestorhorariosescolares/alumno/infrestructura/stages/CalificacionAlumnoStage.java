package com.gamma.gestorhorariosescolares.alumno.infrestructura.stages;

import com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores.CalificacionAlumnoControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionInscripcionData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class CalificacionAlumnoStage extends CustomStage {

    public CalificacionAlumnoStage(EvaluacionInscripcionData evaluacion) {
        if (evaluacion == null)
            throw new NullPointerException("La calificacion no puede ser nula");

        setTitle("Registrar Calificacion");
        cargarFormulario(evaluacion);
    }

    private void cargarFormulario(EvaluacionInscripcionData evaluacion) {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        try {
            AnchorPane panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "evaluacion/infrestructura/vistas/CalificacionAlumno.fxml",
                    new CalificacionAlumnoControlador(this, evaluacion)
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}