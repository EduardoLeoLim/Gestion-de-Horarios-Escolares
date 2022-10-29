package com.gamma.gestorhorariosescolares.secretario.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;
import org.sql2o.Connection;

import java.util.List;

public class MySql2oSecretarioRepositorio implements SecretarioRepositorio {

    private final Connection conexion;

    public MySql2oSecretarioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Secretario> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Secretario secretario) {
        return 0;
    }

    @Override
    public void actualizar(Secretario secretario) {

    }
}
