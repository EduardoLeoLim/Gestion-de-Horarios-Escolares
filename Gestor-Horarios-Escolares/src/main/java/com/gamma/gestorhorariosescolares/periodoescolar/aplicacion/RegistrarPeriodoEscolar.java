package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.excepciones.SuperposicionRangoFechasException;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.registrar.RegistradorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

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

    public void registrar(String clave, String nombre, Date fechaInicio, Date fechaFin) throws ClaveDuplicadaException,
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
        periodosEscolares = buscadorPeriodoEscolar
                .mayorIgualQue("fechaInicio", fechaInicio.toString())
                .menorIgualQue("fechaFin", fechaInicio.toString())
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de inicio se superpone con otros periodos escolares");

        periodosEscolares = buscadorPeriodoEscolar
                .mayorIgualQue("fechaInicio", fechaFin.toString())
                .menorIgualQue("fechaFin", fechaFin.toString())
                .buscar();
        if (!periodosEscolares.isEmpty())
            throw new SuperposicionRangoFechasException("La fecha de finalización se superpone con otros periodos escolares");


        registradorPeriodoEscolar.registrar(clave, nombre, fechaInicio, fechaFin);
    }
}
