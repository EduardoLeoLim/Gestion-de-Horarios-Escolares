package com.gamma.gestorhorariosescolares.infrestructura.usuario.persistencia;

import com.gamma.gestorhorariosescolares.dominio.compartido.Criterio;
import com.gamma.gestorhorariosescolares.dominio.usuario.Usuario;
import com.gamma.gestorhorariosescolares.dominio.usuario.UsuarioRepositorio;
import org.sql2o.Connection;

import java.util.List;

public class MysqlUsuarioRepositorio implements UsuarioRepositorio {

    private final Connection connection;

    public MysqlUsuarioRepositorio(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Usuario> buscar(Criterio criterio) {
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
