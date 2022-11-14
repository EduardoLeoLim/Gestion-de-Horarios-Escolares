package com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.grado.dominio.GradoRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oGradoRepositorio implements GradoRepositorio {

    private final Connection conexion;

    public MySql2oGradoRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Grado> buscar(Criteria criterio) {
        List<Grado> grados = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("grado", criterio);

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
            int idPlanEstudio = fila.getInteger("idPlanEstudio");
            boolean estatus = fila.getBoolean("estatus");

            Grado grado = new Grado(id, clave, nombre, idPlanEstudio, estatus);
            grados.add(grado);
        });

        return grados;
    }

    @Override
    public int registrar(Grado grado) {
        String consulta = "INSERT INTO grado (clave, nombre, estatus, idPlanEstudio) VALUES (:clave, :nombre, :estatus, :idPlanEstudio);";
        int idGrado = conexion.createQuery(consulta)
                .addParameter("clave", grado.clave())
                .addParameter("nombre", grado.nombre())
                .addParameter("estatus", grado.estatus())
                .addParameter("idPlanEstudio", grado.idPlanEstudio())
                .executeUpdate()
                .getKey(Integer.class);
        return idGrado;
    }

    @Override
    public void actualizar(Grado grado) {
        String consultaSelect = "SELECT * FROM grado WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE grado SET clave=:clave, nombre=:nombre, estatus=:estatus, idPlanEstudio=:idPlanEstudio WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", grado.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", grado.clave())
                .addParameter("nombre", grado.nombre())
                .addParameter("estatus", grado.estatus())
                .addParameter("idPlanEstudio", grado.idPlanEstudio())
                .executeUpdate();
    }

    @Override
    public void eliminar(int idGrado) {
        String consulta = "DELETE FROM grado WHERE (id = :id);";
        conexion.createQuery(consulta)
                .addParameter("id", idGrado)
                .executeUpdate();
    }

}
