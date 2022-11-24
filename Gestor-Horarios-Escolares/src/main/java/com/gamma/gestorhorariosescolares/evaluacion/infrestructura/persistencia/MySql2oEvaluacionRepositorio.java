package com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.EvaluacionRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oEvaluacionRepositorio implements EvaluacionRepositorio {

    private final Connection conexion;

    public MySql2oEvaluacionRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Evaluacion> buscar(Criteria criterio) {
        List<Evaluacion> evaluaciones = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("evaluacion", criterio);

        consulta = conversorCriteria.generarConsultaSql2o();
        List<Map<Integer, String>> parametros = conversorCriteria.generarParametrosSql2o();

        Query query = conexion.createQuery(consulta);
        parametros.forEach(parametro -> {
            query.addParameter(parametro.get(0), parametro.get(1));
        });
        Table tabla = query.executeAndFetchTable();
        List<Row> filas = tabla.rows();
        filas.forEach(fila -> {
            Integer id = fila.getInteger("id");
            String calificacion = fila.getString("calificacion");
            String tipo = fila.getString("tipo");
            Integer idMateria = fila.getInteger("idMateria");
            Integer idAlumno = fila.getInteger("idAlumno");
            Evaluacion evaluacion = new Evaluacion(id, calificacion, tipo, idMateria, idAlumno);
            evaluaciones.add(evaluacion);
        });

        return evaluaciones;
    }

    @Override
    public  int registar(Evaluacion evaluacion){
        String consulta = "INSERT INTO evaluacion (calificacion, tipo, idMateria, idAlumno) " +
                "VALUES (:calificacion, :tipo, :idMateria, :idAlumno);";

        int idEvaluacion = conexion.createQuery(consulta)
                .addParameter("calificacion", evaluacion.calificacion())
                .addParameter("tipo", evaluacion.tipo())
                .addParameter("idMateria", evaluacion.idMateria())
                .addParameter("idAlumno", evaluacion.idAlumno())
                .executeUpdate()
                .getKey(Integer.class);
        return idEvaluacion;

    }
}
