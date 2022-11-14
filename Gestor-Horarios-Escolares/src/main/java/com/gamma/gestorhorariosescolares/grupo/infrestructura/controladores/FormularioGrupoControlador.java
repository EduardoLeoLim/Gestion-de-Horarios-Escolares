package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.registrar.RegistradorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.grado.aplicacion.BuscarGrados;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradosData;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.RegistrarGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.registrar.RegistradorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.BuscarPlanesEstudio;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanesEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.buscar.BuscadorPlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.infrestructura.persistencia.MySql2oPlanEstudioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioGrupoControlador {

    private final Stage stage;

    @FXML
    private ComboBox<PlanEstudioData> cbxPlanEstudio;
    @FXML
    private ComboBox<GradoData> cbxGrado;
    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;

    public FormularioGrupoControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        inicializarCbxPlanEstudio();
        inicializarCbxGrado();
        inicializarCbxPeriodoEscolar();

        buscarPlanesEstudio();
        buscarPeriodosEscolares();
    }

    private void inicializarCbxPlanEstudio() {
        cbxPlanEstudio.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PlanEstudioData planEstudio, boolean empty) {
                super.updateItem(planEstudio, empty);
                setText("");
                if (empty || planEstudio == null)
                    return;

                setText(planEstudio.clave() + " - " + planEstudio.nombre());
            }
        });

        cbxPlanEstudio.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(PlanEstudioData planEstudio, boolean empty) {
                super.updateItem(planEstudio, empty);
                setText("");
                if (empty || planEstudio == null)
                    return;

                setText(planEstudio.clave() + " - " + planEstudio.nombre());
            }
        });
    }

    private void inicializarCbxGrado() {
        cbxGrado.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GradoData grado, boolean empty) {
                super.updateItem(grado, empty);
                setText("");
                if (empty || grado == null)
                    return;

                setText(grado.clave() + " - " + grado.nombre());
            }
        });

        cbxGrado.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(GradoData grado, boolean empty) {
                super.updateItem(grado, empty);
                setText("");
                if (empty || grado == null)
                    return;

                setText(grado.clave() + " - " + grado.nombre());
            }
        });

        cbxPlanEstudio.valueProperty().addListener((observable, oldValue, newValue) -> {
            cbxGrado.getItems().clear();

            if (newValue == null)
                return;

            GradosData grados;

            Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

            try (Connection transaccion = conexion.beginTransaction()) {
                //Repositorio
                var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);

                //Servicios
                var buscadorGrado = new BuscadorGrado(gradoRepositorio);

                BuscarGrados buscarGrados = new BuscarGrados(buscadorGrado);

                grados = buscarGrados.buscarHabilitadosPorPlanEstudio(newValue.id());
                cbxGrado.getItems().addAll(grados.grados());
            } catch (Sql2oException e) {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
                mensaje.setTitle("Error base de datos");
                mensaje.showAndWait();
            }
        });
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

    private void buscarPlanesEstudio() {
        PlanesEstudioData planesEstudio;

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var planEstudioRepositorio = new MySql2oPlanEstudioRepositorio(transaccion);

            //Servicios
            var buscadorPlanEstudio = new BuscadorPlanEstudio(planEstudioRepositorio);

            BuscarPlanesEstudio buscarPlanesEstudio = new BuscarPlanesEstudio(buscadorPlanEstudio);

            planesEstudio = buscarPlanesEstudio.buscarHabilitados();
            cbxPlanEstudio.getItems().clear();
            cbxPlanEstudio.getItems().addAll(planesEstudio.planesEstudio());

        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
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

            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarHabilitados();

            cbxPeriodoEscolar.getItems().addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    @FXML
    private void guardarGrupo() {
        GradoData grado = cbxGrado.getValue();
        PeriodoEscolarData periodoEscolar = cbxPeriodoEscolar.getValue();
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();

        if (!sonValidosDatosFormulario(grado, periodoEscolar, clave, nombre))
            return;

        registrarGrupo(grado, periodoEscolar, clave, nombre);
    }

    private Boolean sonValidosDatosFormulario(GradoData grado, PeriodoEscolarData periodoEscolar, String clave, String nombre) {
        if (grado == null || periodoEscolar == null || clave.isBlank() || nombre.isBlank()) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacíos en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacíos");
            mensaje.showAndWait();
            return false;
        }
        return true;
    }

    private void registrarGrupo(GradoData grado, PeriodoEscolarData periodoEscolar, String clave, String nombre) {

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var grupoRepositorio = new MySql2oGrupoRepositorio(transaccion);
            var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);
            var claseRepositorio = new MySql2oClaseRepositorio(transaccion);

            //Servicios
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);
            var registradorGrupo = new RegistradorGrupo(grupoRepositorio);
            var registradorClase = new RegistradorClase(claseRepositorio);

            RegistrarGrupo registrarGrupo = new RegistrarGrupo(buscadorGrupo, buscadorGrado, buscadorMateria,
                    buscadorPeriodoEscolar, registradorGrupo, registradorClase);

            registrarGrupo.registrar(clave, nombre, grado.id(), periodoEscolar.id());

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Grupo registrado correctamente.", ButtonType.OK);
            mensaje.setTitle("Registro exitoso");
            mensaje.showAndWait();
            cerrarFormulario();

        } catch (ClaveDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Clave duplicada");
            mensaje.showAndWait();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Recurso no encontrado");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
            cerrarFormulario();
        }
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }

}
