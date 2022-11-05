package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.excepciones.SuperposicionRangoFechasException;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.actualizar.ActualizadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ActualizarPeriodoEscolar {

    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;
    private final ActualizadorPeriodoEscolar actualizadorPeriodoEscolar;

    public ActualizarPeriodoEscolar(ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar,
                                    ActualizadorPeriodoEscolar actualizadorPeriodoEscolar) {
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
        this.actualizadorPeriodoEscolar = actualizadorPeriodoEscolar;
    }

    public void actualizar(PeriodoEscolarData periodoEscolarData) throws RecursoNoEncontradoException, ClaveDuplicadaException, SuperposicionRangoFechasException {
        List<PeriodoEscolar> periodosEscolares;

        //Validaciones
        periodosEscolares = buscadorPeriodoEscolar
                .igual("id", periodoEscolarData.id().toString())
                .buscar();
        if (periodosEscolares.isEmpty())
            throw new RecursoNoEncontradoException("El periodo escolar que se desea actualizar no se encuentra registrado en el sistema.");

        //¿Ya hay un periodo escolar registrado con la clave especificada?
        periodosEscolares = buscadorPeriodoEscolar
                .diferente("id", periodoEscolarData.id().toString())
                .igual("clave", periodoEscolarData.clave())
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un periodo escolar registrado con la clave " + periodoEscolarData.clave());

        //Superposición de fechas con fechaInicio
        periodosEscolares = buscadorPeriodoEscolar
                .diferente("id", periodoEscolarData.id().toString())
                .mayorIgualQue("fechaInicio", periodoEscolarData.fechaInicioFormateada())
                .menorIgualQue("fechaFin", periodoEscolarData.fechaInicioFormateada())
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de inicio se superpone con otros periodos escolares");

        periodosEscolares = buscadorPeriodoEscolar
                .diferente("id", periodoEscolarData.id().toString())
                .mayorIgualQue("fechaInicio", periodoEscolarData.fechaFinFormateada())
                .menorIgualQue("fechaFin", periodoEscolarData.fechaFinFormateada())
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de finalización se superpone con otros periodos escolares");

        Date fechaInicio = Date.from(periodoEscolarData.fechaInicio().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date fechaFin = Date.from(periodoEscolarData.fechaFin().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        PeriodoEscolar periodoEscolar = new PeriodoEscolar(periodoEscolarData.id(), periodoEscolarData.clave(),
                periodoEscolarData.nombre(), fechaInicio, fechaFin, periodoEscolarData.estatus());

        //Actualizar
        actualizadorPeriodoEscolar.actualizar(periodoEscolar);
    }
}
