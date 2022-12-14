package com.gamma.gestorhorariosescolares.secretario.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.secretario.dominio.Secretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oSecretarioRepositorio implements SecretarioRepositorio {

    private final Connection conexion;

    public MySql2oSecretarioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Buscar secretarios que cumplan con los filtros especificados
     *
     * @param criterio Criterios que deben cumplir los secretarios
     * @return Lista de secretarios
     */
    @Override
    public List<Secretario> buscar(Criteria criterio) {
        List<Secretario> secretarios = new ArrayList<>();
        String consulta;

        //Se añade filtro de tipo como obligatorio para solo obtener secretarios
        Filter filtroTipoEmpleado = Filter.create("tipo", "=", "Secretario");
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
            Secretario secretario = new Secretario(id, estatus, noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
            secretarios.add(secretario);
        });

        return secretarios;
    }

    /**
     * Registrar nuevo secretario
     *
     * @param secretario Secretario que se registrará
     * @return Identificador asignado al secretario al ser registrado
     */
    @Override
    public int registrar(Secretario secretario) {
        String consulta = "INSERT INTO empleado (noPersonal, nombre, apellidoPaterno, apellidoMaterno, estatus, tipo, idUsuario) " +
                "VALUES (:noPersonal, :nombre, :apellidoPaterno, :apellidoMaterno, :estatus, :tipo, :idUsuario);";

        int idSecretario = conexion.createQuery(consulta)
                .addParameter("noPersonal", secretario.noPersonal())
                .addParameter("nombre", secretario.nombre())
                .addParameter("apellidoPaterno", secretario.apellidoPaterno())
                .addParameter("apellidoMaterno", secretario.apellidoMaterno())
                .addParameter("estatus", secretario.estatus())
                .addParameter("tipo", "Secretario")
                .addParameter("idUsuario", secretario.idUsuario())
                .executeUpdate()
                .getKey(Integer.class);

        return idSecretario;
    }

    /**
     * Actualizar un secretario
     *
     * @param secretario Secretario que se actualizará
     */
    @Override
    public void actualizar(Secretario secretario) {
        String consultaSelect = "SELECT * FROM empleado WHERE id = :id FOR UPDATE;";
        String consultaUpdate = "UPDATE empleado SET noPersonal=:noPersonal, nombre=:nombre, apellidoPaterno=:apellidoPaterno, " +
                "apellidoMaterno=:apellidoMaterno, estatus=:estatus WHERE id=:id;";

        conexion.createQuery(consultaSelect)
                .addParameter("id", secretario.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("noPersonal", secretario.noPersonal())
                .addParameter("nombre", secretario.nombre())
                .addParameter("apellidoPaterno", secretario.apellidoPaterno())
                .addParameter("apellidoMaterno", secretario.apellidoMaterno())
                .addParameter("estatus", secretario.estatus())
                .addParameter("id", secretario.id())
                .executeUpdate();
    }
}
