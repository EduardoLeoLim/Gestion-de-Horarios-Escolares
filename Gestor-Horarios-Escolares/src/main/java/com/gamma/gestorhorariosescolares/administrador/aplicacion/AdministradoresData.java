package com.gamma.gestorhorariosescolares.administrador.aplicacion;

import java.util.List;

public class AdministradoresData {

    private final List<AdministradorData> administradores;

    public AdministradoresData(List<AdministradorData> administadores){
        this.administradores = administadores;
    }

    public List<AdministradorData> administradores(){
        return administradores;
    }

}
