package com.gamma.gestorhorariosescolares.clase.dominio;

import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroId;

public class Clase {
    private final ClaseId id;
    private final int idGrupo;
    private final int idMateria;
    private MaestroId idMaestro;

    private final Integer idGrado;
    private final Integer idPeriodoEscolar;

    public Clase(ClaseId id, int idGrupo, int idMateria, MaestroId idMaestro, Integer idGrado, Integer idPeriodoEscolar) {
        this.id = id;
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idMaestro = idMaestro;
        this.idGrado = idGrado;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public Clase(int idGrupo, int idMateria) {
        this.id = new ClaseId(0);
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idMaestro = new MaestroId(0);
        this.idGrado = 0;
        this.idPeriodoEscolar = 0;
    }

    public ClaseId id() {
        return id;
    }

    public int idGrupo() {
        return idGrupo;
    }

    public int idMateria() {
        return idMateria;
    }

    public MaestroId idMaestro() {
        return idMaestro;
    }

    public void asignarIdMaestro(int idMaestro) {
        this.idMaestro = new MaestroId(idMaestro);
    }

    public Integer idGrado() {
        return idGrado;
    }

    public Integer idPeriodoEscolar() {
        return idPeriodoEscolar;
    }
}
