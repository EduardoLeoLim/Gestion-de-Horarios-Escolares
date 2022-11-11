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
