package com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.InscripcionRepositorio;

import java.util.List;

public class MySql2oInscripcionRepositorio implements InscripcionRepositorio {
    @Override
    public List<Inscripcion> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Inscripcion inscripcion) {
        return 0;
    }

    @Override
    public int actualizar(Inscripcion inscripcion) {
        return 0;
    }
}
