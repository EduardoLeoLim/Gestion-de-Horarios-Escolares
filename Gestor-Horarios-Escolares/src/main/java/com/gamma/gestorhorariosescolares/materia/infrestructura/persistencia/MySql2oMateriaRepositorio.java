package com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.materia.dominio.MateriaRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oMateriaRepositorio implements MateriaRepositorio {

    private final Connection conexion;

    public MySql2oMateriaRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Materia> buscar(Criteria criterio) {
        List<Materia> materias = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("materia", criterio);

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
            int horasPracticas = fila.getInteger("horasPracticas");
            int horasTeoricas = fila.getInteger("horasTeoricas");
            int idGrado = fila.getInteger("idGrado");
            boolean estatus = fila.getBoolean("estatus");
            Materia materia = new Materia(id, clave, nombre, horasPracticas, horasTeoricas, idGrado, estatus);
            materias.add(materia);
        });

        return materias;
    }

    @Override
    public int registrar(Materia materia) {
        String consulta = "INSERT INTO materia (clave, nombre, horasPracticas, horasTeoricas, estatus, idGrado) " +
                "VALUES (:clave, :nombre, :horasPracticas, :horasTeoricas, :estatus, :idGrado);";

        int idMateria = conexion.createQuery(consulta)
                .addParameter("clave", materia.clave())
                .addParameter("nombre", materia.nombre())
                .addParameter("horasPracticas", materia.horasPracticas())
                .addParameter("horasTeoricas", materia.horasTeoricas())
                .addParameter("estatus", materia.estatus())
                .addParameter("idGrado", materia.idGrado())
                .executeUpdate()
                .getKey(Integer.class);
        return idMateria;
    }

    @Override
    public void actualizar(Materia materia) {
        String consultaSelect = "SELECT * FROM materia WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE materia SET  clave=:clave, nombre=:nombre, horasPracticas=:horasPracticas, " +
                "horasTeoricas=:horasTeoricas, estatus=:estatus, idGrado=:idGrado WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", materia.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", materia.clave())
                .addParameter("nombre", materia.nombre())
                .addParameter("horasPracticas", materia.horasPracticas())
                .addParameter("horasTeoricas", materia.horasTeoricas())
                .addParameter("estatus", materia.estatus())
                .addParameter("idGrado", materia.idGrado())
                .addParameter("id", materia.id())
                .executeUpdate();
    }

}