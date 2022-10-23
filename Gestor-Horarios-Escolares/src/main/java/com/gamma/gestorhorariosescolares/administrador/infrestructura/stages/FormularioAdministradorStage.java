package com.gamma.gestorhorariosescolares.administrador.infrestructura.stages;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores.FormularioAdministradorControlador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormularioAdministradorStage extends CustomStage {

    private AdministradorData administradorEdicion;

    private final boolean esNuevoRegistro;

    private AnchorPane panelFormulario;

    private FormularioAdministradorControlador controladorFormulario;

    public FormularioAdministradorStage() {
        setTitle("Registrar Administrador");
        esNuevoRegistro = true;

        setMinWidth(400);
        setMinHeight(500);
        setWidth(400);
        setHeight(500);
        setResizable(false);
        initModality(Modality.WINDOW_MODAL);

        cargarFormulario();
    }

    public FormularioAdministradorStage(AdministradorData administradorEdicion) {
        if (administradorEdicion == null)
            throw new NullPointerException();
        setTitle("Editar Administrador");
        this.administradorEdicion = administradorEdicion;
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
            controladorFormulario = new FormularioAdministradorControlador(this);
        else
            controladorFormulario = new FormularioAdministradorControlador(this, administradorEdicion);

        try {
            panelFormulario = InicializarPanel.inicializarAnchorPane(
                    "administrador/infrestructura/vistas/FormularioAdministrador.fxml",
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
