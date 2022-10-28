package com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Temporizador extends Timer {
    private final Temporizador instancia;
    private final int tiempoReinicio;
    private final Consumer<Temporizador> funcionFinalTemporizador;
    private int tiempoEsperaBusqueda;
    private boolean finalizado;

    public Temporizador(int tiempoReinicio, Consumer<Temporizador> funcionFinalTemporizador) {
        instancia = this;
        this.tiempoReinicio = tiempoReinicio;
        tiempoEsperaBusqueda = 0;
        finalizado = false;
        this.funcionFinalTemporizador = funcionFinalTemporizador;
        cargarTarea();
    }

    private void cargarTarea() {
        schedule(new TimerTask() {
            @Override
            public void run() {
                if (tiempoEsperaBusqueda == 0 && finalizado) {
                    funcionFinalTemporizador.accept(instancia);
                    finalizado = false;
                    return;
                }
                if (tiempoEsperaBusqueda > 0) {
                    tiempoEsperaBusqueda--;
                    if (tiempoEsperaBusqueda == 0)
                        finalizado = true;
                }
            }
        }, 0, 1000);
    }

    public void reiniciar() {
        tiempoEsperaBusqueda = tiempoReinicio;
    }
}
