package com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;

import java.util.List;

public interface ServicioBuscadorAdministrador {

    ServicioBuscadorAdministrador igual(String campo, String valor);

    ServicioBuscadorAdministrador diferente(String campo, String valor);

    ServicioBuscadorAdministrador mayorQue(String campo, String valor);

    ServicioBuscadorAdministrador mayorIgualQue(String campo, String valor);

    ServicioBuscadorAdministrador menorQue(String campo, String valor);

    ServicioBuscadorAdministrador menorIgualQue(String campo, String valor);

    ServicioBuscadorAdministrador contiene(String campo, String valor);

    ServicioBuscadorAdministrador noContiene(String campo, String valor);

    ServicioBuscadorAdministrador ordenarAscendente(String campo);

    ServicioBuscadorAdministrador ordenarDescendente(String campo);

    ServicioBuscadorAdministrador intervalo(int intervalo);

    ServicioBuscadorAdministrador limite(int limite);

    List<Administrador> buscar();
}
