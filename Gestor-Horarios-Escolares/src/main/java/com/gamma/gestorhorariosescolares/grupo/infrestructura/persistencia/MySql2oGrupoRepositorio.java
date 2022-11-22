package com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.grupo.dominio.GrupoRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oGrupoRepositorio implements GrupoRepositorio {

    Connection conexion;

    public MySql2oGrupoRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Grupo> buscar(Criteria criterio) {
        List<Grupo> grupos = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("grupo", criterio);

        consulta = conversorCriteria.generarConsultaSql2o();
        List<Map<Integer, String>> parametros = conversorCriteria.generarParametrosSql2o();

        Query query = conexion.createQuery(consulta);
        parametros.forEach(parametro -> {
            query.addParameter(parametro.get(0), parametro.get(1));
        });
        Table tabla = query.executeAndFetchTable();
        List<Row> filas = tabla.rows();

        String consultaInscripciones = "SELECT idInscripcion FROM asignacion WHERE idGrupo=:idGrupo;";

        filas.forEach(fila -> {
            int id = fila.getInteger("id");
            String clave = fila.getString("clave");
            String nombre = fila.getString("nombre");
            int idGrado = fila.getInteger("idGrado");
            int idPeriodoEscolar = fila.getInteger("idPeriodoEscolar");

            int[] idInscripciones = conexion.createQuery(consultaInscripciones)
                    .addParameter("idGrupo", id)
                    .executeScalarList(Integer.class)
                    .stream().mapToInt(Integer::intValue).toArray();

            Grupo grupo = new Grupo(id, clave, nombre, idGrado, idInscripciones, idPeriodoEscolar);
            grupos.add(grupo);
        });
        return grupos;
    }

    @Override
    public int registrar(Grupo grupo) {
        String consultaGrupo = "INSERT INTO grupo (clave, nombre, idGrado, idPeriodoEscolar) " +
                "VALUES (:clave, :nombre, :idGrado, :idPeriodoEscolar);";

        int idGrupo = conexion.createQuery(consultaGrupo)
                .addParameter("clave", grupo.clave())
                .addParameter("nombre", grupo.nombre())
                .addParameter("idGrado", grupo.idGrado())
                .addParameter("idPeriodoEscolar", grupo.idPeriodoEscolar())
                .executeUpdate()
                .getKey(Integer.class);
        return idGrupo;
    }

    @Override
    public void actualizar(Grupo grupo) {
        String consultaSelect = "SELECT * FROM grupo WHERE id = :id FOR UPDATE;";
        Query query = conexion.createQuery(consultaSelect);
        query.addParameter("id", grupo.id())
                .executeAndFetchTable();

        String consultaUpdate = "UPDATE grupo SET clave=:clave, nombre=:nombre, idGrado=:idGrado, " +
                "idPeriodoEscolar=:idPeriodoEscolar WHERE id = :id;";
        query = conexion.createQuery(consultaUpdate);
        query.addParameter("clave", grupo.clave())
                .addParameter("nombre", grupo.nombre())
                .addParameter("idGrado", grupo.idGrado())
                .addParameter("idPeriodoEscolar", grupo.idPeriodoEscolar())
                .addParameter("id", grupo.id())
                .executeUpdate();

        String consultaDelete = "DELETE FROM asignacion WHERE idGrupo = :idGrupo";
        consultaDelete += grupo.idInscripciones().length > 0 ? " AND idInscripcion NOT IN (:idInscripciones);" : ";";

        query = conexion.createQuery(consultaDelete);
        query.addParameter("idGrupo", grupo.id());
        if (grupo.idInscripciones().length > 0) {
            query.addParameter("idInscripciones", grupo.idInscripciones());
        }
        query.executeUpdate();

        if (grupo.idInscripciones().length == 0)
            return;

        String consultaInsert = "INSERT IGNORE INTO asignacion (idInscripcion, idGrupo) VALUES (:idInscripcion, :idGrupo);";
        query = conexion.createQuery(consultaInsert);
        for (int idInscripcion : grupo.idInscripciones()) {
            query.addParameter("idInscripcion", idInscripcion)
                    .addParameter("idGrupo", grupo.id())
                    .addToBatch();
        }
        query.executeBatch();
    }
}
