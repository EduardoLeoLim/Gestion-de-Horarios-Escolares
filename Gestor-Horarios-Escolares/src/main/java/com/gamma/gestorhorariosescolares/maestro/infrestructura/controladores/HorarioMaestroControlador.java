package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import javafx.stage.Stage;

public class HorarioMaestroControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;

    public HorarioMaestroControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }

}