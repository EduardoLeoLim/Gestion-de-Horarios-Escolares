package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.materia.aplicacion.BuscarMaterias;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriasData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class HorarioAlumnoControlador {

    private final Stage stage;
    private Temporizador temporizadorBusqueda;

    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private ComboBox<MateriaData> cbxMateria;

    public HorarioAlumnoControlador(Stage stage){

        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;


    }

    public void initialize(){
        inicializarCbxPeriodoEscolar();
        buscarPeriodosEscolares();
        inicializarCbxMateria();
        buscarMaterias();

    }

    private void inicializarCbxPeriodoEscolar() {
        cbxPeriodoEscolar.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                if (empty || periodoEscolar == null)
                    return;

                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });

        cbxPeriodoEscolar.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                if (empty || periodoEscolar == null)
                    return;

                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });
    }


    private void buscarPeriodosEscolares() {
        cbxPeriodoEscolar.getItems().clear();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);

            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarTodos();

            cbxPeriodoEscolar.getItems().addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    private void inicializarCbxMateria() {
        cbxMateria.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(MateriaData materia, boolean empty) {
                super.updateItem(materia, empty);
                if (empty || materia == null)
                    return;

                setText(materia.clave() + " - " + materia.nombre());
            }
        });

        cbxMateria.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(MateriaData materia, boolean empty) {
                super.updateItem(materia, empty);
                if (empty || materia == null)
                    return;

                setText(materia.clave() + " - " + materia.nombre());
            }
        });
    }

    private void buscarMaterias() {
        cbxMateria.getItems().clear();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);

            //Servicios
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);





        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }





    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
