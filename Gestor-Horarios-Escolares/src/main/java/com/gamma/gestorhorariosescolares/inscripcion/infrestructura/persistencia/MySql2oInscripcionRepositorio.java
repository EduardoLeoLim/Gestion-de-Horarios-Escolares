package com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.MySqlCriteriaParser;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.InscripcionRepositorio;
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

public class MySql2oInscripcionRepositorio implements InscripcionRepositorio {

    private final Connection conexion;

    public MySql2oInscripcionRepositorio(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Inscripcion> buscar(Criteria criterio) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String consulta;

        MySqlCriteriaParser conversorCriteria = new MySqlCriteriaParser("inscripcion", criterio);

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
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaRegistro;
            try {
                fechaRegistro = formatoFecha.parse(fila.getString("fechaRegistro"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Integer idGrado = fila.getInteger("idGrado");
            Integer idPeriodoEscolar = fila.getInteger("idPeriodoEscolar");
            Integer idAlumno = fila.getInteger("idAlumno");

            inscripciones.add(new Inscripcion(id, fechaRegistro, idGrado, idPeriodoEscolar, idAlumno));
        });
        return inscripciones;
    }

    @Override
    public int registrar(Inscripcion inscripcion) {
        return 0;
    }

    @Override
    public int actualizar(Inscripcion inscripcion) {
        return 0;
    }
}
