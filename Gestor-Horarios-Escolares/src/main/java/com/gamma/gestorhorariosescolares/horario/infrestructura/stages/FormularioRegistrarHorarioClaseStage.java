package com.gamma.gestorhorariosescolares.horario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorarioDisponibleData;
import com.gamma.gestorhorariosescolares.horario.infrestructura.controladores.FormularioRegistrarHorarioClaseControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioRegistrarHorarioClaseStage extends CustomStage {

    public FormularioRegistrarHorarioClaseStage(HorarioDisponibleData horarioDisponible) {
        if (horarioDisponible == null)
            throw new NullPointerException("El horario disponible no puede ser nulo");

        setTitle("Registrar Clase");
        cargarFormulario(horarioDisponible);
    }

    private void cargarFormulario(HorarioDisponibleData horarioDisponible) {
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        try {
            AnchorPane panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "horario/infrestructura/vistas/FormularioRegistrarHorarioClase.fxml",
                    new FormularioRegistrarHorarioClaseControlador(this, horarioDisponible)
            );
            panelFormulario.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(panelFormulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
