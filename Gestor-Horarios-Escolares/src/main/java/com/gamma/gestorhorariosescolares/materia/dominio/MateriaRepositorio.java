package com.gamma.gestorhorariosescolares.materia.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface MateriaRepositorio {
    List<Materia> buscar(Criteria criterio);

    int registrar(Materia materia);

    void actualizar(Materia materia);

    void eliminar(int idMateria);
}
