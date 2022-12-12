package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseMateriaMaestroData;
import javafx.stage.Stage;

public class InformacionGrupoControlador {

    private final Stage stage;

    private final ClaseMateriaMaestroData clase;

    public InformacionGrupoControlador(Stage stage, ClaseMateriaMaestroData clase ) {
        this.stage = stage;
        stage.setTitle("Informacion del grupo");
        this.clase = clase;

    }





}
