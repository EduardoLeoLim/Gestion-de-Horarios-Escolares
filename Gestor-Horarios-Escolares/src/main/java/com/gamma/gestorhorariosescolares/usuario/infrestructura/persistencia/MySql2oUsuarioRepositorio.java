package com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oUsuarioRepositorio implements UsuarioRepositorio {

    private final Connection conexion;

    public MySql2oUsuarioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Usuario> buscar(Criteria criterio) {
        List<Usuario> usuarios = new ArrayList<>();

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("usuario", criterio);
        String consulta = conversorCriteria.generarConsultaSql2o();
        List<Map<Integer, String>> parametros = conversorCriteria.generarParametrosSql2o();

        Query query = conexion.createQuery(consulta);
        System.out.println(consulta);

        parametros.forEach(parametro -> {
            query.addParameter(parametro.get(0), parametro.get(1));
        });

        Table tabla = query.executeAndFetchTable();
        List<Row> filas = tabla.rows();

        filas.forEach(fila -> {
            int id = fila.getInteger("id");
            String telefono = fila.getString("telefono");
            String correoElectronico = fila.getString("correoElectronico");
            String claveAcceso = fila.getString("claveAcceso");
            String tipo = fila.getString("tipo");
            Usuario usuario = new Usuario(id, telefono, correoElectronico, claveAcceso, tipo);
            usuarios.add(usuario);
        });

        return usuarios;
    }

    @Override
    public int registrar(Usuario usuario) {
        return 0;
    }

    @Override
    public void actualizar(Usuario usuario) {

    }
}
