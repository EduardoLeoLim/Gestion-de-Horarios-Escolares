package com.gamma.gestorhorariosescolares.horario.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.horario.dominio.Horario;
import com.gamma.gestorhorariosescolares.horario.dominio.HorarioRepositorio;
import com.gamma.gestorhorariosescolares.maestro.dominio.MaestroId;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySql2oHorarioRepositorio implements HorarioRepositorio {

    private final Connection conexion;

    public MySql2oHorarioRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Horario> buscar(Criteria criterio) {
        List<Horario> horarios = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("horariodetalle", criterio);

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
            int diaSemana = fila.getInteger("diaSemana");
            LocalTime horaInicio = LocalTime.parse(fila.getString("horaInicio"));
            LocalTime horaFin = LocalTime.parse(fila.getString("horaFin"));
            int idMateria = fila.getInteger("idMateria");
            int idGrupo = fila.getInteger("idGrupo");
            int idClase = fila.getInteger("idClase");
            MaestroId idMaestro;
            if (fila.getInteger("idMaestro") == null) {
                idMaestro = null;
            } else {
                idMaestro = new MaestroId(fila.getInteger("idMaestro"));
            }
            int idEdificio = fila.getInteger("idEdificio");
            int idSalon = fila.getInteger("idSalon");
            int idPeriodoEscolar = fila.getInteger("idPeriodoEscolar");

            Horario horario = new Horario(id, diaSemana, horaInicio, horaFin, idMateria, idGrupo, idClase, idMaestro,
                    idEdificio, idSalon, idPeriodoEscolar);
            horarios.add(horario);
        });

        return horarios;
    }

    @Override
    public int registrar(Horario horario) {
        String consulta = "INSERT INTO horario (diaSemana, horaInicio, horaFin, idClase, idSalon) " +
                "VALUES (:diaSemana, :horaInicio, :horaFin, :idClase, :idSalon)";

        Integer idHorario = conexion.createQuery(consulta)
                .addParameter("diaSemana", horario.diaSemana())
                .addParameter("horaInicio", horario.horaInicio())
                .addParameter("horaFin", horario.horaFin())
                .addParameter("idClase", horario.idClase())
                .addParameter("idSalon", horario.idSalon())
                .executeUpdate()
                .getKey(Integer.class);

        return idHorario;
    }

    @Override
    public void actualizar(Horario horario) {
        String consulta = "UPDATE horario SET diaSemana = :diaSemana, horaInicio = :horaInicio, horaFin = :horaFin, " +
                "idClase = :idClase, idSalon = :idSalon WHERE id = :id";

        conexion.createQuery(consulta)
                .addParameter("diaSemana", horario.diaSemana())
                .addParameter("horaInicio", horario.horaInicio())
                .addParameter("horaFin", horario.horaFin())
                .addParameter("idClase", horario.idClase())
                .addParameter("idSalon", horario.idSalon())
                .addParameter("id", horario.id())
                .executeUpdate();
    }

    @Override
    public void eliminar(int idHorario) {
        String consulta = "DELETE FROM horario WHERE id = :id";

        conexion.createQuery(consulta)
                .addParameter("id", idHorario)
                .executeUpdate();
    }

}