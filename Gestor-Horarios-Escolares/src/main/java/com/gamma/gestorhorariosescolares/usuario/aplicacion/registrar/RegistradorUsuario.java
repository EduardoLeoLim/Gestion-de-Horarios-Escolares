package com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

public class RegistradorUsuario implements ServicioRegistradorUsuario {

    public final UsuarioRepositorio repositorio;

    public RegistradorUsuario(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String correoElectronico, String claveAcceso) {
        //Validar formato de correoElectrnocio
            //Si no es valido, lanzar excepción
        //Validar formato de usuario
            //Si no es válido, lanzar excepción

        Usuario nuevoUsuario = new Usuario(correoElectronico, claveAcceso);
        return repositorio.registrar(nuevoUsuario);
    }
}
