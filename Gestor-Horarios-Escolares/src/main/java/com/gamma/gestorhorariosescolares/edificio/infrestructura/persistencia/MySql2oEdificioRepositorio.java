package com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.edificio.dominio.Edificio;
import com.gamma.gestorhorariosescolares.edificio.dominio.EdificioRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oEdificioRepositorio implements EdificioRepositorio {

    private final Connection conexion;

    public MySql2oEdificioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Edificio> buscar(Criteria criterio) {
        List<Edificio> edificios = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("edificio", criterio);

        consulta = conversorCriteria.generarConsultaSql2o();
        List<Map<Integer, String>> parametros = conversorCriteria.generarParametrosSql2o();

        Query query = conexion.createQuery(consulta);
        parametros.forEach(parametro -> {
            query.addParameter(parametro.get(0), parametro.get(1));
        });
        Table tabla = query.executeAndFetchTable();
        List<Row> filas = tabla.rows();
        filas.forEach(fila -> {
            int id = fila.getInteger("id");
            String clave = fila.getString("clave");
            String nombre = fila.getString("nombre");
            boolean estatus = fila.getBoolean("estatus");

            Edificio edificio = new Edificio(id, clave, nombre, estatus);
            edificios.add(edificio);
        });

        return edificios;
    }

    @Override
    public int registrar(Edificio edificio) {
        String consulta = "INSERT INTO edificio (clave, nombre, estatus) VALUES (:clave, :nombre, :estatus);";

        int idEdificio = conexion.createQuery(consulta)
                .addParameter("clave", edificio.clave())
                .addParameter("nombre", edificio.nombre())
                .addParameter("estatus", edificio.estatus())
                .executeUpdate()
                .getKey(Integer.class);
        return idEdificio;
    }

    @Override
    public void actualizar(Edificio edificio) {
        String consultaSelect = "SELECT * FROM edificio WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE edificio SET clave=:clave, nombre=:nombre, estatus=:estatus WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", edificio.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", edificio.clave())
                .addParameter("nombre", edificio.nombre())
                .addParameter("estatus", edificio.estatus())
                .addParameter("id", edificio.id())
                .executeUpdate();
    }
}
