package com.gamma.gestorhorariosescolares.edificio.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;

import java.util.List;

public class BuscarEdificios {

    private final ServicioBuscador<Edificio> buscadorEdificio;

    public BuscarEdificios(ServicioBuscador<Edificio> buscadorEdificio) {
        this.buscadorEdificio = buscadorEdificio;
    }

    public EdificiosData buscarTodos() {
        List<Edificio> listaEdificios = buscadorEdificio
                .ordenarAscendente("clave")
                .buscar();

        return new EdificiosData(listaEdificios);
    }

    public EdificiosData buscarHabilitados() {
        List<Edificio> listaEdificios = buscadorEdificio
                .igual("estatus", "1")
                .ordenarAscendente("clave")
                .buscar();

        return new EdificiosData(listaEdificios);
    }

    public EdificiosData buscarPorCriterio(String criterio) {
        List<Edificio> listaEdificios = buscadorEdificio
                .contiene("clave", criterio).esOpcional()
                .contiene("nombre", criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        return new EdificiosData(listaEdificios);
    }
}
