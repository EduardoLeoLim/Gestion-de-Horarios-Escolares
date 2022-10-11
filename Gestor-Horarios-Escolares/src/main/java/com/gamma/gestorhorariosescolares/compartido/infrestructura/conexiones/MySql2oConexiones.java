package com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones;

import org.sql2o.Sql2o;

public class MySql2oConexiones {
    public static Sql2o getConexionPrimaria(){
        String cadenaConexion = "jdbc:mysql://localhost:3306/gestor_escolar";
        return new Sql2o(cadenaConexion,"root", "contraseña");
    }
}
