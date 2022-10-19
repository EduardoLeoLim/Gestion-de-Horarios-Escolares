package com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.materia.dominio.MateriaRepositorio;

import java.util.List;

public class MySql2oMateriaRepositorio implements MateriaRepositorio {
    @Override
    public List<Materia> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Materia materia) {
        return 0;
    }

    @Override
    public void actualizar(Materia materia) {

    }
}
