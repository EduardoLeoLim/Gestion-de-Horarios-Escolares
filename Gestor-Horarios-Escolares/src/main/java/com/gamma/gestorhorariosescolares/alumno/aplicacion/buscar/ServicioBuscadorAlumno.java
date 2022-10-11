package com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar;

import com.gamma.gestorhorariosescolares.alumno.dominio.Alumno;

import java.util.List;

public interface ServicioBuscadorAlumno {
    ServicioBuscadorAlumno igual(String campo, String valor);
    ServicioBuscadorAlumno diferente(String campo, String valor);
    ServicioBuscadorAlumno mayorQue(String campo, String valor);
    ServicioBuscadorAlumno mayorIgualQue(String campo, String valor);
    ServicioBuscadorAlumno menorQue(String campo, String valor);
    ServicioBuscadorAlumno menorIgualQue(String campo, String valor);
    ServicioBuscadorAlumno contiene(String campo, String valor);
    ServicioBuscadorAlumno noContiene(String campo, String valor);
    ServicioBuscadorAlumno ordenarAscendente(String campo);
    ServicioBuscadorAlumno ordenarDescendente(String campo);
    ServicioBuscadorAlumno intervalo(int intervalo);
    ServicioBuscadorAlumno limite(int limite);

    List<Alumno> buscar();

}
