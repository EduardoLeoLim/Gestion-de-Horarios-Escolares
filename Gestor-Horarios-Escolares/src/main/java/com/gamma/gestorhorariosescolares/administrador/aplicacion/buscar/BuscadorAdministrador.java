package com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;

import java.util.List;

public class BuscadorAdministrador implements ServicioBuscadorAdministrador{
    private final AdministradorRepositorio repositorio;
    private Criterio criterio;

    public BuscadorAdministrador(AdministradorRepositorio repositorio){
        this.repositorio = repositorio;
        criterio= new Criterio();
    }


    @Override
    public BuscadorAdministrador filtrarId(int id) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public BuscadorAdministrador filtrarNoPersonal(String noPersonal) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public BuscadorAdministrador filtarNombre(String nombre) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public BuscadorAdministrador filtrarApellidoPaterno(String apellidoPaterno) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public BuscadorAdministrador filtrarApellidoMaterno(String apellidoMaterno) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public BuscadorAdministrador filtarrIdUsuario(int id) {
        //Criterio.setFiltro(filtro)
        return this;
    }

    @Override
    public List<Administrador> buscar() {
        List<Administrador> resultado = repositorio.buscar(criterio);
        criterio = new Criterio();
        return resultado;
    }
}
