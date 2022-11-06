package com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolarRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MySql2oPeriodoEscolarRepositorio implements PeriodoEscolarRepositorio {

    private final Connection conexion;

    public MySql2oPeriodoEscolarRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<PeriodoEscolar> buscar(Criteria criterio) {
        List<PeriodoEscolar> periodosEscolares = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("periodoescolar", criterio);

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
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = null;
            Date fechaFin = null;
            try {
                fechaInicio = formatoFecha.parse(fila.getString("fechaInicio"));
                fechaFin = formatoFecha.parse(fila.getString("fechaFin"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            boolean estatus = fila.getBoolean("estatus");

            PeriodoEscolar periodoEscolar = new PeriodoEscolar(id, clave, nombre, fechaInicio, fechaFin, estatus);
            periodosEscolares.add(periodoEscolar);
        });

        return periodosEscolares;
    }

    @Override
    public int registrar(PeriodoEscolar periodoEscolar) {
        String consulta = "INSERT INTO periodoescolar (clave, nombre, fechaInicio, fechaFin, estatus) " +
                "VALUES (:clave, :nombre, :fechaInicio, :fechaFin, :estatus);";
        int idPeriodoEscolar = conexion.createQuery(consulta)
                .addParameter("clave", periodoEscolar.clave())
                .addParameter("nombre", periodoEscolar.nombre())
                .addParameter("fechaInicio", periodoEscolar.fechaInicio())
                .addParameter("fechaFin", periodoEscolar.fechaFin())
                .addParameter("estatus", periodoEscolar.estatus())
                .executeUpdate()
                .getKey(Integer.class);
        return idPeriodoEscolar;
    }

    @Override
    public void actualizar(PeriodoEscolar periodoEscolar) {
        String consultaSelect = "SELECT * FROM periodoescolar WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE periodoescolar SET clave=:clave, nombre=:nombre, fechaInicio=:fechaInicio, " +
                "fechaFin=:fechaFin, estatus=:estatus WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", periodoEscolar.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", periodoEscolar.clave())
                .addParameter("nombre", periodoEscolar.nombre())
                .addParameter("fechaInicio", periodoEscolar.fechaInicio())
                .addParameter("fechaFin", periodoEscolar.fechaFin())
                .addParameter("estatus", periodoEscolar.estatus())
                .addParameter("id", periodoEscolar.id())
                .executeUpdate();
    }
}
