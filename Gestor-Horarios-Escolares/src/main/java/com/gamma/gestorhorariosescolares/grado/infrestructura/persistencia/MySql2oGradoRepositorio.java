package com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grado.dominio.GradoRepositorio;
import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

public class MySql2oGradoRepositorio implements GradoRepositorio {

    private final Connection conexion;

    public MySql2oGradoRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Grado> buscar(Criteria criterio) {
        List<Grado> grados = new ArrayList<>();
        return grados;
    }

    @Override
    public int registrar(Grado grado) {
        return 0;
    }

    @Override
    public int actualizar(Grado grado) {
        return 0;
    }

    @Override
    public void eliminar(int idGrado) {

    }

}
