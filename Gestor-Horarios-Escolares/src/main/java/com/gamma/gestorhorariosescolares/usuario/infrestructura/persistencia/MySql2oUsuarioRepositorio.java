package com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;
import org.sql2o.Connection;

import java.util.List;

public class MySql2oUsuarioRepositorio implements UsuarioRepositorio {

    private final Connection connection;

    public MySql2oUsuarioRepositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Usuario> buscar(Criteria criterio) {
        return null;
    }

    @Override
    public int registrar(Usuario usuario) {
        return 0;
    }

    @Override
    public void actualizar(Usuario usuario) {

    }

    @Override
    public void eliminar(int idUsuario) {

    }
}
