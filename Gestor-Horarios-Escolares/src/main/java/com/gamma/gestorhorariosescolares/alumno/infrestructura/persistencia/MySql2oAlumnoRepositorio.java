package com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oAlumnoRepositorio implements AlumnoRepositorio {

    private final Connection conexion;

    public MySql2oAlumnoRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Buscar lista de alumnos que cumplan el criterio especificado
     *
     * @param criterio Define los filtros que deben cumplir los alumnos al buscarlos
     * @return Lista de alumnos
     */
    @Override
    public List<Alumno> buscar(Criteria criterio) {
        List<Alumno> alumnos = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("alumno", criterio);

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
            boolean estatus = fila.getBoolean("estatus");
            String matricula = fila.getString("matricula");
            String curp = fila.getString("curp");
            String nombre = fila.getString("nombre");
            String apellidoPaterno = fila.getString("apellidoPaterno");
            String apellidoMaterno = fila.getString("apellidoMaterno");
            int idUsuario = fila.getInteger("idUsuario");
            Alumno alumno = new Alumno(id, estatus, matricula, curp, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
            alumnos.add(alumno);
        });

        return alumnos;
    }

    /**
     * Registrar nuevo alumno
     *
     * @param alumno Alumno que se registrará
     * @return Identificador asignado al alumno al ser registrado
     */
    @Override
    public int registrar(Alumno alumno) {
        String consulta = "INSERT INTO alumno (matricula, nombre, apellidoPaterno, apellidoMaterno, curp, estatus, idUsuario) " +
                "VALUES (:matricula, :nombre, :apellidoPaterno, :apellidoMaterno, :curp, :estatus, :idUsuario);";

        int idAlumno = conexion.createQuery(consulta)
                .addParameter("matricula", alumno.matricula())
                .addParameter("nombre", alumno.nombre())
                .addParameter("apellidoPaterno", alumno.apellidoPaterno())
                .addParameter("apellidoMaterno", alumno.apellidoMaterno())
                .addParameter("curp", alumno.curp())
                .addParameter("estatus", alumno.estaHabilitado())
                .addParameter("idUsuario", alumno.idUsuario())
                .executeUpdate()
                .getKey(Integer.class);
        return idAlumno;
    }

    /**
     * Actualizar un alumno
     *
     * @param alumno Alumno que será actualizado
     */
    @Override
    public void actualizar(Alumno alumno) {
        String consultaSelect = "SELECT * FROM alumno WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE alumno SET matricula=:matricula, curp=:curp, nombre=:nombre, " +
                "apellidoPaterno=:apellidoPaterno, apellidoMaterno=:apellidoMaterno, estatus=:estatus WHERE id=:id;";

        conexion.createQuery(consultaSelect)
                .addParameter("id", alumno.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("matricula", alumno.matricula())
                .addParameter("curp", alumno.curp())
                .addParameter("nombre", alumno.nombre())
                .addParameter("apellidoPaterno", alumno.apellidoPaterno())
                .addParameter("apellidoMaterno", alumno.apellidoMaterno())
                .addParameter("estatus", alumno.estaHabilitado())
                .addParameter("id", alumno.id())
                .executeUpdate();
    }
}
