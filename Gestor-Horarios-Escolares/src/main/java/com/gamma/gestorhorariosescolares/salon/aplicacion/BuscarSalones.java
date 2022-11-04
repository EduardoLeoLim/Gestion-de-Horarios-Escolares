package com.gamma.gestorhorariosescolares.salon.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarSalones {

    private final ServicioBuscador<Salon> buscadorSalon;
    private final ServicioBuscador<Edificio> buscadorEdificio;

    public BuscarSalones(ServicioBuscador<Salon> buscadorSalon, ServicioBuscador<Edificio> buscadorEdificio) {
        this.buscadorSalon = buscadorSalon;
        this.buscadorEdificio = buscadorEdificio;
    }

    public SalonesData buscarTodos() {
        List<Salon> listaSalones = buscadorSalon
                .ordenarAscendente("clave")
                .buscar();

        List<SalonData> listaSalonesData = listaSalones.stream().map(salon -> {
            Edificio edificio;
            try {
                edificio = buscadorEdificio.igual("id", String.valueOf(salon.idEdificio())).buscarPrimero();
                return SalonData.fromAggregate(salon, edificio);
            } catch (RecursoNoEncontradoException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return new SalonesData(listaSalonesData);
    }

    public SalonesData buscarHabilitados() {
        List<Salon> listaSalones = buscadorSalon
                .igual("estatus", "1")
                .ordenarAscendente("clave")
                .buscar();

        List<SalonData> listaSalonesData = listaSalones.stream().map(salon -> {
            Edificio edificio;
            try {
                edificio = buscadorEdificio.igual("id", String.valueOf(salon.idEdificio())).buscarPrimero();
                return SalonData.fromAggregate(salon, edificio);
            } catch (RecursoNoEncontradoException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return new SalonesData(listaSalonesData);
    }

    public SalonesData buscarSalonesDeEdificio(int idEdificio) {
        List<Salon> listaSalones = buscadorSalon
                .igual("idEdificio", String.valueOf(idEdificio))
                .ordenarAscendente("clave")
                .buscar();

        List<SalonData> listaSalonesData = listaSalones.stream().map(salon -> {
            Edificio edificio;
            try {
                edificio = buscadorEdificio.igual("id", String.valueOf(salon.idEdificio())).buscarPrimero();
                return SalonData.fromAggregate(salon, edificio);
            } catch (RecursoNoEncontradoException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return new SalonesData(listaSalonesData);
    }

    public SalonesData buscarPorCriterio(String criterio) {
        List<Salon> listaSalones = buscadorSalon
                .contiene("clave", criterio).esOpcional()
                .contiene("capacidad", criterio).esOpcional()
                .ordenarAscendente("clave")
                .buscar();

        List<SalonData> listaSalonesData = listaSalones.stream().map(salon -> {
            Edificio edificio;
            try {
                edificio = buscadorEdificio.igual("id", String.valueOf(salon.idEdificio())).buscarPrimero();
                return SalonData.fromAggregate(salon, edificio);
            } catch (RecursoNoEncontradoException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return new SalonesData(listaSalonesData);
    }

}