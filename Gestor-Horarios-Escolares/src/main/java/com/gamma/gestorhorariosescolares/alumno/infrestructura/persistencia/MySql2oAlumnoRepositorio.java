package com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;
import com.gamma.gestorhorariosescolares.alumno.dominio.AlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.dominio.Criterio;
import org.sql2o.Connection;

import java.util.List;

public class MySql2oAlumnoRepositorio implements AlumnoRepositorio {

    private final Connection connection;

    public MySql2oAlumnoRepositorio(Connection connection){
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
