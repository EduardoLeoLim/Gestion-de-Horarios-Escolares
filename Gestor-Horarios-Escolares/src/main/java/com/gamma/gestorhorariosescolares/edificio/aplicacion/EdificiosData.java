package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

import java.util.List;
import java.util.stream.Collectors;

public class EdificiosData {
    private final List<EdificioData> edificios;

    public EdificiosData(List<Edificio> edificios) {
        if (edificios == null)
            throw new NullPointerException();

        this.edificios = edificios.stream().map(EdificioData::fromAggregate).collect(Collectors.toList());
    }

    public List<EdificioData> edificios() {
        return edificios;
    }
}
