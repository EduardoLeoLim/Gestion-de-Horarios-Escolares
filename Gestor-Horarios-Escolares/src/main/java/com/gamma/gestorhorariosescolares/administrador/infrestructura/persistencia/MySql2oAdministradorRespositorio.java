package com.gamma.gestorhorariosescolares.administrador.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oAdministradorRespositorio implements AdministradorRepositorio {

    private final Connection conexion;

    public MySql2oAdministradorRespositorio(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Buscar lista de Administradores que cumplan el criterio especificado
     *
     * @param criterio El parámetro criterio define los filtros que los administradores deben cumplir al buscarlos
     * @return Lista de Administradores
     */
    @Override
    public List<Administrador> buscar(Criteria criterio) {
        List<Administrador> administradores = new ArrayList<>();
        String consulta;

        //Se añade filtro de tipo como opbligatorio para solo obtener administradores
        Filter filtroTipoEmpleado = Filter.create("tipo", "=", "Administrador");
        filtroTipoEmpleado.obligatory();
        criterio.filters().filters().add(filtroTipoEmpleado);

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("empleado", criterio);

        consulta = conversorCriteria.generarConsultaSql2o();
        List<Map<Integer, String>> parametros = conversorCriteria.generarParametrosSql2o();
        System.out.println(consulta);

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
            Administrador administrador = new Administrador(id, estatus, noPersonal, nombre, apellidoPaterno, apellidoMaterno, idUsuario);
            administradores.add(administrador);
        });

        return administradores;
    }

    /**
     * Registrar nuevo administrador
     *
     * @param administrador El parametro administrador es el Administrador que se registrará
     * @return Identificador asignado al administrador al ser registrado
     */
    @Override
    public int registrar(Administrador administrador) {
        return 0;
    }

    /**
     * Actualizar el administrador
     *
     * @param administrador El parametro administrador es el Administrador que se actualizará
     */
    @Override
    public void actualizar(Administrador administrador) {

    }

}