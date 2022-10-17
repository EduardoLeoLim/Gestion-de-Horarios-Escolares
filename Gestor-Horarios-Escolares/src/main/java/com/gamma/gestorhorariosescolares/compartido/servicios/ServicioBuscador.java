package com.gamma.gestorhorariosescolares.compartido.servicios;

import java.util.List;

public interface ServicioBuscador<T> {
    ServicioBuscador<T> igual(String campo, String valor);

    ServicioBuscador<T> diferente(String campo, String valor);

    ServicioBuscador<T> mayorQue(String campo, String valor);

    ServicioBuscador<T> mayorIgualQue(String campo, String valor);

    ServicioBuscador<T> menorQue(String campo, String valor);

    ServicioBuscador<T> menorIgualQue(String campo, String valor);

    ServicioBuscador<T> contiene(String campo, String valor);

    ServicioBuscador<T> noContiene(String campo, String valor);

    ServicioBuscador<T> ordenarAscendente(String campo);

    ServicioBuscador<T> ordenarDescendente(String campo);

    ServicioBuscador<T> intervalo(int intervalo);

    ServicioBuscador<T> limite(int limite);

    List<T> buscar();
}
