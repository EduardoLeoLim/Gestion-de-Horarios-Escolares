package com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Temporizador extends Timer {
    private final Temporizador instancia;
    private int tiempoEseraBusquesa;
    private final int tiempoReinicio;
    private boolean finalizado;
    private final Consumer<Temporizador> funcionFinalTemporizador;

    public Temporizador(int tiempoReinicio, Consumer<Temporizador> funcionFinalTemporizador) {
        instancia = this;
        this.tiempoReinicio = tiempoReinicio;
        tiempoEseraBusquesa = 0;
        finalizado = false;
        this.funcionFinalTemporizador = funcionFinalTemporizador;
        cargagarTarea();
    }

    private void cargagarTarea() {
        schedule(new TimerTask() {
            @Override
            public void run() {
                if (tiempoEseraBusquesa == 0 && finalizado) {
                    funcionFinalTemporizador.accept(instancia);
                    finalizado = false;
                    return;
                }
                if (tiempoEseraBusquesa > 0) {
                    tiempoEseraBusquesa--;
                    if (tiempoEseraBusquesa == 0)
                        finalizado = true;
                }
            }
        },0, 1000);
    }

    public void reiniciar() {
        tiempoEseraBusquesa = tiempoReinicio;
    }
}
