package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.registrar.RegistradorMateria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;

import java.util.List;

public class RegistrarMateria {

    private final ServicioBuscador<Materia> buscadorMateria;
    private final RegistradorMateria registradorMateria;

    public RegistrarMateria(ServicioBuscador<Materia> buscadorMateria, RegistradorMateria registradorMateria) {
        this.buscadorMateria = buscadorMateria;
        this.registradorMateria = registradorMateria;
    }

    public void registrar(String clave, String nombre, int horasPracticas, int horasTeoricas) throws ClaveDuplicadaException {
        //Validaciones
        List<Materia> materias = buscadorMateria
                .igual("clave", clave)
                .buscar();

        if (!materias.isEmpty())
            throw new ClaveDuplicadaException("Ya hay una materia registrada con la clave " + clave);

        //Registro
        registradorMateria.registrar(clave, nombre, horasPracticas, horasTeoricas);
    }

}