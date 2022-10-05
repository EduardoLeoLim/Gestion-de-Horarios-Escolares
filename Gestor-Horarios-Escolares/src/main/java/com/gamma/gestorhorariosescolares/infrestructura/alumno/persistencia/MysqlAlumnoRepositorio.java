package com.gamma.gestorhorariosescolares.infrestructura.alumno.persistencia;

import com.gamma.gestorhorariosescolares.dominio.alumno.Alumno;
import com.gamma.gestorhorariosescolares.dominio.alumno.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.dominio.compartido.Criterio;
import org.sql2o.Connection;

import java.util.List;

public class MysqlAlumnoRepositorio implements AlumnoRepositorio {

    private final Connection connection;

    public MysqlAlumnoRepositorio(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Alumno> buscar(Criterio criterio) {
        return null;
    }

    @Override
    public int registrar(Alumno alumno) {
        return 0;
    }

    @Override
    public void actualizar(Alumno alumno) {

    }

    @Override
    public void eliminar(int idAlumno) {

    }
}
