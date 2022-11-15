package com.gamma.gestorhorariosescolares.grupo.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.time.Instant;
import java.util.Collections;
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

    public GruposData buscar(String clave, String nombre, Integer idGrado, Integer idPeriodoEscolar) {
        if (clave != null && !clave.isBlank())
            buscadorGrupo.igual("clave", clave);
        if (nombre != null && !nombre.isBlank())
            buscadorGrupo.igual("nombre", nombre);
        if (idGrado != null)
            buscadorGrupo.igual("idGrado", idGrado.toString());
        if (idPeriodoEscolar != null)
            buscadorGrupo.igual("idPeriodoEscolar", idPeriodoEscolar.toString());
        buscadorGrupo.ordenarAscendente("clave");

        List<Grupo> grupos = buscadorGrupo.buscar();

        return new GruposData(prepararResultado(grupos));
    }

    public GruposData buscarPorCoincidencia(String clave, String nombre, Integer idGrado, Integer idPeriodoEscolar) {
        if (clave != null && !clave.isBlank())
            buscadorGrupo.contiene("clave", clave).esOpcional();
        if (nombre != null && !nombre.isBlank())
            buscadorGrupo.contiene("nombre", nombre).esOpcional();
        if (idGrado != null)
            buscadorGrupo.contiene("idGrado", idGrado.toString()).esOpcional();
        if (idPeriodoEscolar != null)
            buscadorGrupo.contiene("idPeriodoEscolar", idPeriodoEscolar.toString()).esOpcional();
        buscadorGrupo.ordenarAscendente("clave");

        List<Grupo> grupos = buscadorGrupo.buscar();

        return new GruposData(prepararResultado(grupos));
    }

    public GrupoData buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Grupo grupo = buscadorGrupo.igual("id", id.toString()).buscarPrimero();
        return prepararResultado(Collections.singletonList(grupo)).get(0);
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
