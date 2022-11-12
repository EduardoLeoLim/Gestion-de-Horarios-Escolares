package com.gamma.gestorhorariosescolares.clase.dominio;

public class Clase {
    private final int id;
    private final int idGrupo;
    private final int idMateria;
    private int idMaestro;

    public Clase(int id, int idGrupo, int idMateria, int idMaestro) {
        this.id = id;
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idMaestro = idMaestro;
    }

    public Clase(int idGrupo, int idMateria) {
        this.id = 0;
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idMaestro = 0;
    }

    public int id() {
        return id;
    }

    public int idGrupo() {
        return idGrupo;
    }

    public int idMateria() {
        return idMateria;
    }

    public int idMaestro() {
        return idMaestro;
    }

    public void asignarIdMaestro(int idMaestro) {
        this.idMaestro = idMaestro;
    }
}
