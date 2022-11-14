package com.gamma.gestorhorariosescolares.materia.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.grado.aplicacion.BuscarGrados;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.aplicacion.GradosData;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.ActualizarMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.MateriaData;
import com.gamma.gestorhorariosescolares.materia.aplicacion.RegistrarMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.actualizar.ActualizadorMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.aplicacion.registrar.RegistradorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
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

public class FormularioMateriaControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final MateriaData materia;

    @FXML
    private ComboBox<PlanEstudioData> cbxPlanEstudio;
    @FXML
    private ComboBox<GradoData> cbxGrado;
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtHorasPracticas;
    @FXML
    private TextField txtHorasTeoricas;

    public FormularioMateriaControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.materia = null;
        this.esNuevoRegistro = true;
    }

    public FormularioMateriaControlador(Stage stage, MateriaData materia) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.materia = materia;
        this.esNuevoRegistro = false;
    }

    @FXML
    public void initialize() {
        cargarComboBoxPlanEstudio();
        cargarComboBoxGrado();

        if (!esNuevoRegistro)
            cargarDatosMateria();
    }

    private void cargarComboBoxPlanEstudio() {
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

        PlanesEstudioData planesEstudio;

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var planEstudioRepositorio = new MySql2oPlanEstudioRepositorio(transaccion);

            //Servicios
            var buscadorPlanEstudio = new BuscadorPlanEstudio(planEstudioRepositorio);

            BuscarPlanesEstudio buscarPlanesEstudio = new BuscarPlanesEstudio(buscadorPlanEstudio);

            planesEstudio = buscarPlanesEstudio.buscarTodos();
            cbxPlanEstudio.getItems().clear();
            cbxPlanEstudio.getItems().addAll(planesEstudio.planesEstudio());

        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    private void cargarComboBoxGrado() {
        cbxGrado.getItems().clear();

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

                grados = buscarGrados.buscarPorPlanEstudio(newValue.id());
                cbxGrado.getItems().addAll(grados.grados());
            } catch (Sql2oException e) {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
                mensaje.setTitle("Error base de datos");
                mensaje.showAndWait();
            }
        });
    }

    private void cargarDatosMateria() {
        PlanEstudioData planEstudioSeleccion = cbxPlanEstudio.getItems().stream()
                .filter(planEstudio -> planEstudio.id() == this.materia.planEstudio().id())
                .findFirst()
                .orElse(null);

        if (planEstudioSeleccion != null)
            cbxPlanEstudio.getSelectionModel().select(planEstudioSeleccion);

        GradoData gradoSeleccion = cbxGrado.getItems().stream()
                .filter(grado -> grado.id() == this.materia.grado().id())
                .findFirst()
                .orElse(null);

        if (gradoSeleccion != null)
            cbxGrado.getSelectionModel().select(gradoSeleccion);

        txtClave.setText(this.materia.clave());
        txtNombre.setText(this.materia.nombre());
        txtHorasPracticas.setText(this.materia.horasPracticas().toString());
        txtHorasTeoricas.setText(this.materia.horasTeoricas().toString());
    }

    @FXML
    private void guardarMateria() {
        GradoData grado = cbxGrado.getSelectionModel().getSelectedItem();
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();
        String horasPracticas = txtHorasPracticas.getText();
        String horasTeoricas = txtHorasTeoricas.getText();

        if (!sonValidosDatosFormulario(grado, clave, nombre, horasPracticas, horasTeoricas))
            return;

        if (esNuevoRegistro)
            registarMateria(clave, nombre, Integer.parseInt(horasPracticas), Integer.parseInt(horasTeoricas), grado);
        else
            actualizarMateria(this.materia.id(), clave, nombre, Integer.parseInt(horasPracticas),
                    Integer.parseInt(horasTeoricas), this.materia.planEstudio(), grado, this.materia.estatus());
    }

    private Boolean sonValidosDatosFormulario(GradoData grado, String clave, String nombre, String horasPracticas,
                                              String horasTeoricas) {
        if (grado == null || clave.isBlank() || nombre.isBlank() || horasPracticas.isBlank() || horasTeoricas.isBlank()) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacios en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacíos");
            mensaje.showAndWait();
            return false;
        }

        try {
            Integer.parseInt(horasPracticas);
            Integer.parseInt(horasTeoricas);
        } catch (NumberFormatException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Formato de horas inválido", ButtonType.OK);
            mensaje.setTitle("Datos inválidos");
            mensaje.showAndWait();
            return false;
        }

        return true;
    }

    private void registarMateria(String clave, String nombre, int horasPracticas, int horasTeoricas, GradoData grado) {

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);
            var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);

            //Servicios
            var busdcadorMateria = new BuscadorMateria(materiaRepositorio);
            var registradorMateria = new RegistradorMateria(materiaRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);

            RegistrarMateria registrarMateria = new RegistrarMateria(busdcadorMateria, buscadorGrado, registradorMateria);
            registrarMateria.registrar(clave, nombre, horasPracticas, horasTeoricas, grado);
            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Materia registrada correctamente", ButtonType.OK);
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

    private void actualizarMateria(int id, String clave, String nombre, int horasPracticas, int horasTeoricas,
                                   PlanEstudioData planEstudio, GradoData grado, boolean estatus) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var materiaRepositorio = new MySql2oMateriaRepositorio(transaccion);
            var gradoRepositorio = new MySql2oGradoRepositorio(transaccion);

            //Servicios
            var busdcadorMateria = new BuscadorMateria(materiaRepositorio);
            var actualizadorMateria = new ActualizadorMateria(materiaRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);

            ActualizarMateria actualizarMateria = new ActualizarMateria(busdcadorMateria, buscadorGrado,
                    actualizadorMateria);

            MateriaData materiaData = new MateriaData(id, clave, nombre, horasPracticas, horasTeoricas, planEstudio,
                    grado, estatus);

            actualizarMateria.actualizar(materiaData);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Materia actualizada correctamente", ButtonType.OK);
            mensaje.setTitle("Actualización exitosa");
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