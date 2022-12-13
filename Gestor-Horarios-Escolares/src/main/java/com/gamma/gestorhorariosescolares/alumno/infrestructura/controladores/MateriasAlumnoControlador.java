package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradoresData;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.BuscarAdministradores;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.persistencia.MySql2oAdministradorRespositorio;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.*;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GruposData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.inscripcion.aplicacion.buscar.BuscadorInscripcion;
import com.gamma.gestorhorariosescolares.inscripcion.infrestructura.persistencia.MySql2oInscripcionRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class MateriasAlumnoControlador {

    private final Stage stage;
    private AlumnoData alumno;
    private Temporizador temporizadorBusqueda;
    private ObservableList coleccionClases;


    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;

    @FXML
    private TableView<ClaseAlumnoData> tablaMateriasAlumno;


    public MateriasAlumnoControlador(Stage stage, AlumnoData alumno){
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        this.alumno = alumno;


    }



    @FXML
    public void initialize(){

        inicializarCbxPeriodoEscolar();
        tablaMateriasAlumno.setDisable(false);
        buscarPeriodosEscolares();
        inicializarTabla();
        if (cbxPeriodoEscolar.getItems().size() > 0){
            cbxPeriodoEscolar.setValue(cbxPeriodoEscolar.getItems().get(0));
        }


    }

    private void inicializarTabla(){
        coleccionClases = FXCollections.observableArrayList();

        TableColumn<ClaseAlumnoData, String> columnaClave = new TableColumn<>("Clave");
        columnaClave.setCellValueFactory(ft-> new SimpleStringProperty(ft.getValue().claseMateriaData().clave()));
        columnaClave.setMinWidth(150);

        TableColumn<ClaseAlumnoData, String> columnaMateria = new TableColumn<>("Materia");
        columnaMateria.setCellValueFactory(ft-> new SimpleStringProperty(ft.getValue().claseMateriaData().nombre()));
        columnaMateria.setMinWidth(150);

        TableColumn<ClaseAlumnoData, String> columnaMaestro = new TableColumn<>("Maestro");
        columnaMaestro.setCellValueFactory(ft-> {
            ClaseMaestroData maestro = ft.getValue().claseMaestroData();
            if (maestro == null)
                return new SimpleStringProperty("Sin asignar");
            return new SimpleStringProperty(maestro.nombre() );
        });

        columnaMaestro.setMinWidth(150);

        tablaMateriasAlumno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaMateriasAlumno.getColumns().addAll(columnaClave, columnaMateria, columnaMaestro);
        tablaMateriasAlumno.setItems(coleccionClases);



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

        cbxPeriodoEscolar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null){
                return;
            }
            buscarMaterias(newValue);
        });
    }

    private void buscarMaterias(PeriodoEscolarData periodoEscolar) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try(Connection conexionBD = conexion.open()){
            var claseRepositorio = new  MySql2oClaseRepositorio(conexionBD);
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexionBD);
            var inscripcionRepositorio = new MySql2oInscripcionRepositorio(conexionBD);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexionBD);
            var maestroRepositorio = new MySql2oMaestroRepositorio(conexionBD);

            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorInscripcion = new BuscadorInscripcion(inscripcionRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);

            BuscarClasesDeAlumno buscarClasesDeAlumno = new BuscarClasesDeAlumno(buscadorClase,buscadorGrupo,
                    buscadorInscripcion,buscadorMateria,buscadorMaestro);

            ClasesAlumnoData clases = buscarClasesDeAlumno.buscar(periodoEscolar, alumno);

            cargarMateriasEnTabla(clases);

        }catch (Sql2oException ex){
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
        }

    }

    private void cargarMateriasEnTabla(ClasesAlumnoData clases){
        coleccionClases.clear();
        coleccionClases.addAll(clases.clasesAlumno());

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



    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }


}
