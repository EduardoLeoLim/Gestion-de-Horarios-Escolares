package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.excepciones.SuperposicionRangoFechasException;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.registrar.RegistradorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class RegistrarPeriodoEscolar {

    private final ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar;
    private final RegistradorPeriodoEscolar registradorPeriodoEscolar;

    public RegistrarPeriodoEscolar(ServicioBuscador<PeriodoEscolar> buscadorPeriodoEscolar,
                                   RegistradorPeriodoEscolar registradorPeriodoEscolar) {
        this.buscadorPeriodoEscolar = buscadorPeriodoEscolar;
        this.registradorPeriodoEscolar = registradorPeriodoEscolar;
    }

    public void registrar(String clave, String nombre, LocalDate fechaInicio, LocalDate fechaFin) throws ClaveDuplicadaException,
            SuperposicionRangoFechasException {
        List<PeriodoEscolar> periodosEscolares;

        //Validaciones
        //¿Ya hay un periodo escolar registrado con la clave especificada?
        periodosEscolares = buscadorPeriodoEscolar
                .igual("clave", clave)
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new ClaveDuplicadaException("Ya hay un periodo escolar registrado con la clave " + clave);

        //Superposición de fechas con fechaInicio
        //yyyy-MM-dd
        String fechaInicioFormateada = fechaInicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String fechaFinFormateada = fechaFin.format(DateTimeFormatter.ISO_LOCAL_DATE);

        periodosEscolares = buscadorPeriodoEscolar
                .mayorIgualQue("fechaInicio", fechaInicioFormateada)
                .menorIgualQue("fechaFin", fechaInicioFormateada)
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de inicio se superpone con otros periodos escolares");

        periodosEscolares = buscadorPeriodoEscolar
                .mayorIgualQue("fechaInicio", fechaFinFormateada)
                .menorIgualQue("fechaFin", fechaFinFormateada)
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de finalización se superpone con otros periodos escolares");

        Date fechaInicioRegistro = Date.from(fechaInicio.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date fechaFinRegistro = Date.from(fechaFin.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        registradorPeriodoEscolar.registrar(clave, nombre, fechaInicioRegistro, fechaFinRegistro);
    }
}
