package com.gamma.gestorhorariosescolares.grupo.infrestructura.stages;

import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaMaestroData;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.InicializarPanel;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.DetallesGrupoControlador;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores.InformacionGrupoControlador;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class InformacionGrupoStage extends CustomStage {
    public InformacionGrupoStage(ClaseMateriaMaestroData clase) {
        setMinHeight(400);
        setMinWidth(900);
        initModality(Modality.WINDOW_MODAL);

        try {
            InformacionGrupoControlador controlador = new InformacionGrupoControlador(this, clase);
            AnchorPane panel = InicializarPanel.inicializarAnchorPane("grupo/infrestructura/vistas/InformacionGrupo.fxml",
                    controlador);
            panel.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet());
            setContent(panel);

        } catch (IOException e) {
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }
}
