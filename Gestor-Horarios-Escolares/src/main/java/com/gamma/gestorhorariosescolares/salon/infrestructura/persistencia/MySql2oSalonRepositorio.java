package com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.salon.dominio.Salon;
import com.gamma.gestorhorariosescolares.salon.dominio.SalonRepositorio;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oSalonRepositorio implements SalonRepositorio {

    private final Connection conexion;

    public MySql2oSalonRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Salon> buscar(Criteria criterio) {
        List<Salon> salones = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("salon", criterio);

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
            String clave = fila.getString("Clave");
            int capacidad = fila.getInteger("capacidad");
            boolean estatus = fila.getBoolean("estatus");
            int idEdificio = fila.getInteger("idEdificio");

            Salon salon = new Salon(id, clave, capacidad, estatus, idEdificio);
            salones.add(salon);
        });

        return salones;
    }

    @Override
    public int registrar(Salon salon) {
        String consulta = "INSERT INTO salon (clave, capacidad, estatus, idEdificio) VALUES (:clave, :capacidad, :estatus, :idEdificio);";

        int idSalon = conexion.createQuery(consulta)
                .addParameter("clave", salon.clave())
                .addParameter("capacidad", salon.capacidad())
                .addParameter("estatus", salon.estatus())
                .addParameter("idEdificio", salon.idEdificio())
                .executeUpdate()
                .getKey(Integer.class);

        return idSalon;
    }

    @Override
    public void actualizar(Salon salon) {
        String consultaSelect = "SELECT * FROM salon WHERE (id = :id) FOR UPDATE;";
        String consultaUpdate = "UPDATE salon SET clave=:clave, capacidad=:capacidad, estatus=:estatus, " +
                "idEdificio=:idEdificio WHERE (id = :id);";

        conexion.createQuery(consultaSelect)
                .addParameter("id", salon.id())
                .executeAndFetchTable();

        conexion.createQuery(consultaUpdate)
                .addParameter("clave", salon.clave())
                .addParameter("capacidad", salon.capacidad())
                .addParameter("estatus", salon.estatus())
                .addParameter("idEdificio", salon.idEdificio())
                .addParameter("id", salon.id())
                .executeUpdate();
    }
}
