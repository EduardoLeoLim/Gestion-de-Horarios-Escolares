package com.gamma.gestorhorariosescolares.clase.aplicacion;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.dominio.Clase;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionData;
import com.gamma.gestorhorariosescolares.evaluacion.dominio.Evaluacion;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.dominio.Grupo;
import com.gamma.gestorhorariosescolares.inscripcion.dominio.Inscripcion;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroClaseData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.dominio.Maestro;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaClaseData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarClasesDeAlumno {
    private final ServicioBuscador<Clase> buscadorClase;
    private final ServicioBuscador<Grupo> buscadorGrupo;
    private final ServicioBuscador<Inscripcion> buscadorInscripcion;
    private final ServicioBuscador<Materia> buscadorMateria;
    private final ServicioBuscador<Maestro> buscadorMaestro;


    public BuscarClasesDeAlumno(ServicioBuscador<Clase> buscadorClase, ServicioBuscador<Grupo> buscadorGrupo, ServicioBuscador<Inscripcion> buscadorInscripcion, ServicioBuscador<Materia> buscadorMateria, ServicioBuscador<Maestro> buscadorMaestro) {
        this.buscadorClase = buscadorClase;
        this.buscadorGrupo = buscadorGrupo;
        this.buscadorInscripcion = buscadorInscripcion;
        this.buscadorMateria = buscadorMateria;
        this.buscadorMaestro = buscadorMaestro;
    }

    public ClasesAlumnoData buscar(PeriodoEscolarData periodoEscolar, AlumnoData alumno){
        Inscripcion inscripcion;
        try{
            inscripcion = buscadorInscripcion.igual("idPeriodoEscolar", periodoEscolar.id().toString()).igual("idAlumno", alumno.id().toString()).buscarPrimero();
            List<Grupo> grupos = buscadorGrupo
                    .igual("idPeriodoEscolar", periodoEscolar.id().toString() )
                    .buscar();

            grupos = grupos.stream().filter(grupo -> grupo.estaInscrito(inscripcion.id())).collect(Collectors.toList());
            if (grupos.isEmpty()){
                return new ClasesAlumnoData(new ArrayList<>());

            }

            Grupo grupo = grupos.get(0);

            List<Clase> clases = buscadorClase.igual("idGrupo", String.valueOf(grupo.id())).buscar();

            List<ClaseAlumnoData> clasesData = clases.stream().map(clase -> {
                Materia materia;
                Maestro maestro;
                ClaseMateriaData claseMateriaData;
                ClaseMaestroData claseMaestroData = null;
                //MateriaData materiaData;
                //MaestroData maestroData = null;



                try {
                    materia = buscadorMateria
                            .igual("id", String.valueOf(clase.idMateria()))
                            .buscarPrimero();
                    claseMateriaData = ClaseMateriaData.fromAggregate(materia);
                    //materiaData = MateriaData.fromAggregate(materia,null, null);
                } catch (RecursoNoEncontradoException e) {
                    claseMateriaData = null;
                    //materiaData = null;
                }

                if (clase.idMaestro() != null) {
                    try {
                        maestro = buscadorMaestro
                                .igual("id", clase.idMaestro().value().toString())
                                .buscarPrimero();
                        claseMaestroData = ClaseMaestroData.fromAggregate(maestro);
                        //maestroData = MaestroData.fromAggregate(maestro, null);
                    } catch (RecursoNoEncontradoException ignored) {

                    }
                }

                return ClaseAlumnoData.fromAggregate(clase,grupo,claseMateriaData, claseMaestroData);








                //return ClaseData.fromAggregate(clase, GrupoData.fromAggregate(grupo, null, null),claseMateriaData, claseMaestroData);
            }).collect(Collectors.toList());

            return new ClasesAlumnoData(clasesData);

        } catch (RecursoNoEncontradoException e) {
            return new ClasesAlumnoData(new ArrayList<>());
        }


    }

}
