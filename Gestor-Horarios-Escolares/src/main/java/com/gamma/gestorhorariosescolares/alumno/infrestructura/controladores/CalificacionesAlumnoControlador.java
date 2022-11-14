package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import javafx.stage.Stage;

public class CalificacionesAlumnoControlador {

    private final Stage stage;

    private Temporizador temporizadorBusqueda;

    public CalificacionesAlumnoControlador(Stage stage){
        this.stage = stage;

    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}
