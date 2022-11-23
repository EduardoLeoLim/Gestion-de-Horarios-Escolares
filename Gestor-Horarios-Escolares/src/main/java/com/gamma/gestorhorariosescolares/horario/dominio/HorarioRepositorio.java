package com.gamma.gestorhorariosescolares.horario.dominio;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;

import java.util.List;

public interface HorarioRepositorio {
    List<Horario> buscar(Criteria criterio);
    int registrar(Horario horario);
    void actualizar(Horario horario);
    void eliminar(int idHorario);
}
