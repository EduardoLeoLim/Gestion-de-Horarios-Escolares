package com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones;

import com.gamma.gestorhorariosescolares.App;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MySql2oConexiones {

    private static Sql2o conexionPrimaria;

    public static Sql2o getConexionPrimaria() {
        if (conexionPrimaria == null) {
            try (InputStream input = App.class.getResourceAsStream("compartido/infrestructura/config.properties")) {
                Properties propiedades = new Properties();
                propiedades.load(input);
                conexionPrimaria = new Sql2o(propiedades.getProperty("db.url"), propiedades.getProperty("db.usuario"), propiedades.getProperty("db.clave"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return conexionPrimaria;
    }
}
