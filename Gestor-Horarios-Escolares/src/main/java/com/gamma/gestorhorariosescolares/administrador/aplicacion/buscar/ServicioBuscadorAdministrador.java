package com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;

import java.util.List;

public interface ServicioBuscadorAdministrador {
    ServicioBuscadorAdministrador filtrarId(int id);
    ServicioBuscadorAdministrador filtrarNoPersonal(String noPersonal);
    ServicioBuscadorAdministrador filtarNombre(String nombre);
    ServicioBuscadorAdministrador filtrarApellidoPaterno(String apellidoPaterno);
    ServicioBuscadorAdministrador filtrarApellidoMaterno(String apellidoMaterno);
    ServicioBuscadorAdministrador filtarrIdUsuario(int id);

    List<Administrador> buscar();
}
