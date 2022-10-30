package com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oMaestroRepositorio implements MaestroRepositorio {

    private final Connection conexion;

    public MySql2oMaestroRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Buscar lista de maestros que cumplan el criterio especificado
     *
     * @param criterio Define los filtros que los maestros deben cumplir
     * @return Lista de Maestros
     */
    @Override
    public List<Maestro> buscar(Criteria criterio) {
        List<Maestro> maestros = new ArrayList<>();
        String consulta;

        //Se añade filtro obligatorio para solo obtener Maestros
        Filter filtroTipoEmpleado = Filter.create("tipo", "=", "Maestro");
        filtroTipoEmpleado.obligatory();
        criterio.filters().filters().add(filtroTipoEmpleado);

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("empleado", criterio);

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
            String noPersonal = fila.getString("noPersonal");
            String nombre = fila.getString("nombre");
            String apellidoPaterno = fila.getString("apellidoPaterno");
            String apellidoMaterno = fila.getString("apellidoMaterno");
            int idUsuario = fila.getInteger("idUsuario");
            Maestro maestro = new Maestro(id, estatus, noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
            maestros.add(maestro);
        });

        return maestros;
    }

    /**
     * Registrar nuevo maestro
     *
     * @param maestro Maestro que se registrará
     * @return Identificador asignado al maestro al ser registrado
     */
    @Override
    public int registrar(Maestro maestro) {
        String consulta = "INSERT INTO empleado (noPersonal, nombre, apellidoPaterno, apellidoMaterno, estatus, tipo, idUsuario) " +
                "VALUES (:noPersonal, :nombre, :apellidoPaterno, :apellidoMaterno, :estatus, :tipo, :idUsuario);";

        int idMaestro = conexion.createQuery(consulta)
                .addParameter("noPersonal", maestro.noPersonal())
                .addParameter("nombre", maestro.nombre())
                .addParameter("apellidoPaterno", maestro.apellidoPaterno())
                .addParameter("apellidoMaterno", maestro.apellidoMaterno())
                .addParameter("estatus", maestro.estatus())
                .addParameter("tipo", "Maestro")
                .addParameter("idUsuario", maestro.idUsuario())
                .executeUpdate()
                .getKey(Integer.class);

        return idMaestro;
    }

    /**
     * Actualizar un maestro
     *
     * @param maestro Maestro que se actualizará
     */
    @Override
    public void actualizar(Maestro maestro) {
        String consultaSelect = "SELECT * FROM empleado WHERE id = :id FOR UPDATE;";
        String consultaUpdate = "UPDATE empleado SET noPersonal=:noPersonal, nombre=:nombre, apellidoPaterno=:apellidoPaterno, " +
                "apellidoMaterno=:apellidoMaterno, estatus=:estatus WHERE id=:id;";

        conexion.createQuery(consultaSelect)
                .addParameter("id",maestro.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("noPersonal", maestro.noPersonal())
                .addParameter("nombre", maestro.nombre())
                .addParameter("apellidoPaterno", maestro.apellidoPaterno())
                .addParameter("apellidoMaterno", maestro.apellidoMaterno())
                .addParameter("estatus", maestro.estatus())
                .addParameter("id", maestro.id())
                .executeUpdate();
    }
}
