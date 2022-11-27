package com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.clase.dominio.ClaseId;
import com.gamma.gestorhorariosescolares.clase.dominio.ClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroId;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oClaseRepositorio implements ClaseRepositorio {

    private final Connection conexion;

    public MySql2oClaseRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Clase> buscar(Criteria criterio) {
        List<Clase> clases = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("clase", criterio);

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
            Integer idGrupo = fila.getInteger("idGrupo");
            Integer idMateria = fila.getInteger("idMateria");
            Integer idMaestro = fila.getInteger("idMaestro");

            Clase clase = new Clase(new ClaseId(id), idGrupo, idMateria, idMaestro == null ? null : new MaestroId(idMaestro));
            clases.add(clase);
        });

        return clases;
    }

    @Override
    public int registar(Clase clase) {
        String consulta = "INSERT INTO clase (idMateria, idGrupo) VALUES (:idMateria, :idGrupo);";

        int idClase = conexion.createQuery(consulta)
                .addParameter("idGrupo", clase.idGrupo())
                .addParameter("idMateria", clase.idMateria())
                .executeUpdate()
                .getKey(Integer.class);

        return idClase;
    }

    @Override
    public void actualizar(Clase clase) {
        String consulta = "UPDATE clase SET idMaestro = :idMaestro WHERE id = :id;";

        conexion.createQuery(consulta)
                .addParameter("idMaestro", clase.idMaestro().value())
                .addParameter("id", clase.id().value())
                .executeUpdate();
    }
}
