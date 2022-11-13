package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarGrupos {

    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Grado> buscadorGrado;
    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;

    public BuscarGrupos(ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Grado> buscadorGrado,
                        ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar) {
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorGrado = buscadorGrado;
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
    }

    public GruposData buscar(String clave, String nombre, GradoData gradoData, PeriodoEscolarData periodoEscolarData) {
        List<Grupo> grupos = buscadorGrupo
                .contiene("clave", clave)
                .contiene("nombre", nombre)
                .contiene("idGrado", gradoData.id().toString())
                .contiene("idPeriodoEscolar", periodoEscolarData.id().toString())
                .ordenarAscendente("clave")
                .buscar();
        return new GruposData(prepararResultado(grupos));
    }

    public GruposData buscarPorCoincidencia(String clave, String nombre, GradoData gradoData, PeriodoEscolarData periodoEscolarData) {
        List<Grupo> grupos = buscadorGrupo
                .contiene("clave", clave).esOpcional()
                .contiene("nombre", nombre).esOpcional()
                .contiene("idGrado", gradoData.id().toString()).esOpcional()
                .contiene("idPeriodoEscolar", periodoEscolarData.id().toString()).esOpcional()
                .ordenarAscendente("clave")
                .buscar();
        return new GruposData(prepararResultado(grupos));
    }

    private List<GrupoData> prepararResultado(List<Grupo> grupos) {
        return grupos.stream().map(grupo -> {
            Grado grado = buscadorGrado
                    .igual("id", String.valueOf(grupo.idGrado()))
                    .buscar()
                    .stream()
                    .findFirst()
                    .orElse(new Grado(0, "", "", 0, false));

            PeriodoEscolar periodoEscolar = buscadorPeriodoEscolar
                    .igual("id", String.valueOf(grupo.idPeriodoEscolar()))
                    .buscar()
                    .stream()
                    .findFirst()
                    .orElse(new PeriodoEscolar(0, "", "", Date.from(Instant.now()), Date.from(Instant.now()), false));
            return GrupoData.fromAggregate(grupo, GradoData.fromAggregate(grado), PeriodoEscolarData.fromAggregate(periodoEscolar));
        }).collect(Collectors.toList());
    }
}
