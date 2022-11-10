package com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudioRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oPlanEstudioRepositorio implements PlanEstudioRepositorio {

    private final Connection conexion;

    public MySql2oPlanEstudioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<PlanEstudio> buscar(Criteria criterio) {
        List<PlanEstudio> planesEstudio = new ArrayList<>();

        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("planestudio", criterio);

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

            PlanEstudio planEstudio = new PlanEstudio(id, clave, nombre, estatus);
            planesEstudio.add(planEstudio);
        });

        return planesEstudio;
    }

    @Override
    public int registrar(PlanEstudio planEstudio) {
        String consulta = "INSERT INTO planestudio (clave, nombre, estatus) VALUES (:clave, :nombre, :estatus);";
        int idPlanEstudio = conexion.createQuery(consulta)
                .addParameter("clave", planEstudio.clave())
                .addParameter("nombre", planEstudio.nombre())
                .addParameter("estatus", planEstudio.estatus())
                .executeUpdate()
                .getKey(Integer.class);
        return idPlanEstudio;
    }

    @Override
    public void actualizar(PlanEstudio planEstudio) {
        String consultaSelect = "SELECT * FROM planestudio WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE planestudio SET clave=:clave, nombre=:nombre, estatus=:estatus WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", planEstudio.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", planEstudio.clave())
                .addParameter("nombre", planEstudio.nombre())
                .addParameter("estatus", planEstudio.estatus())
                .addParameter("id", planEstudio.id())
                .executeUpdate();
    }

    @Override
    public void eliminar(int idPlanEstudio) {
        String consulta = "DELETE FROM planestudio WHERE (id = :id);";
        conexion.createQuery(consulta)
                .addParameter("id", idPlanEstudio)
                .executeUpdate();
    }

}