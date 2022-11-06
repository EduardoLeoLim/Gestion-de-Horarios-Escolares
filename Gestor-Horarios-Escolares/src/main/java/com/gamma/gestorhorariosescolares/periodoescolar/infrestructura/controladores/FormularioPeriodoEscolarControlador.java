package com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.materia.aplicacion.excepciones.SuperposicionRangoFechasException;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.ActualizarPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.RegistrarPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.actualizar.ActualizadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.registrar.RegistradorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FormularioPeriodoEscolarControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final PeriodoEscolarData periodoEscolar;

    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;
    @FXML
    private DatePicker dpckFechaInicio;
    @FXML
    private DatePicker dpckFechaFin;

    public FormularioPeriodoEscolarControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.periodoEscolar = null;
    }

    public FormularioPeriodoEscolarControlador(Stage stage, PeriodoEscolarData periodoEscolar) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.periodoEscolar = periodoEscolar;
    }

    @FXML
    public void initialize() {
        cargarConfiguracionFormulario();

        if (!esNuevoRegistro)
            cargarDatosPeriodoEscolar();
    }

    private void cargarConfiguracionFormulario() {
//        dpckFechaInicio.setDayCellFactory(ft -> new DateCell() {
//            @Override
//            public void updateItem(LocalDate item, boolean empty) {
//                super.updateItem(item, empty);
//                setDisable(!item.isAfter(LocalDate.now()) && !item.isEqual(LocalDate.now()));
//            }
//        });
        dpckFechaInicio.setOnAction(event -> {
            dpckFechaFin.setDisable(false);
            dpckFechaFin.setValue(null);
            dpckFechaFin.setDayCellFactory(ft -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(!item.isAfter(dpckFechaInicio.getValue()));
                }
            });
        });
        dpckFechaFin.setOnAction(event -> {
            if (dpckFechaInicio.getValue() == null || dpckFechaFin.getValue() == null)
                return;

            String nombreParteInicio = dpckFechaInicio.getValue().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()));
            String nombreParteFinal = dpckFechaFin.getValue().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()));

            txtNombre.setText(nombreParteInicio + " - " + nombreParteFinal);
        });
    }

    private void cargarDatosPeriodoEscolar() {
        if (periodoEscolar == null)
            throw new NullPointerException();

        txtClave.setText(this.periodoEscolar.clave());
        txtNombre.setText(this.periodoEscolar.nombre());
        dpckFechaInicio.setValue(this.periodoEscolar.fechaInicio());
        dpckFechaFin.setDisable(false);
        dpckFechaFin.setValue(this.periodoEscolar.fechaFin());
    }

    @FXML
    private void guardarPeriodoEscolar() {
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();
        LocalDate fechaInicio = dpckFechaInicio.getValue();
        LocalDate fechaFin = dpckFechaFin.getValue();

        if (!sonValidosDatosFormulario(clave, nombre, fechaInicio, fechaFin))
            return;

        if (esNuevoRegistro)
            registrarPeriodoEscolar(clave, nombre, fechaInicio, fechaFin);
        else
            actualizarPeriodoEscolar(this.periodoEscolar.id(), clave, nombre, fechaInicio, fechaFin, this.periodoEscolar.estatus());
    }

    private boolean sonValidosDatosFormulario(String clave, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        if (clave.isBlank() || nombre.isBlank() || fechaInicio == null || fechaFin == null) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacios en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacios");
            mensaje.showAndWait();
            return false;
        }

        return true;
    }

    private void registrarPeriodoEscolar(String clave, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);
            var registradorPeriodoEscolar = new RegistradorPeriodoEscolar(periodoEscolarRepositorio);

            RegistrarPeriodoEscolar registrarPeriodoEscolar = new RegistrarPeriodoEscolar(buscadorPeriodoEscolar, registradorPeriodoEscolar);
            registrarPeriodoEscolar.registrar(clave, nombre, fechaInicio, fechaFin);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Periodo escolar registrado correctamente", ButtonType.OK);
            mensaje.setTitle("Registro exitoso");
            mensaje.showAndWait();

            cerrarFormulario();
        } catch (ClaveDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Clave duplicada");
            mensaje.showAndWait();
        } catch (SuperposicionRangoFechasException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Superposición de fechas");
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no diponible", ButtonType.OK);
            mensaje.setTitle("Error de base de datos");
            mensaje.showAndWait();
            cerrarFormulario();
        }
    }

    private void actualizarPeriodoEscolar(Integer id, String clave, String nombre, LocalDate fechaInicio, LocalDate fechaFin, Boolean estatus) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorio
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(transaccion);

            //Servicioa
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);
            var actualizadorPeriodoEscolar = new ActualizadorPeriodoEscolar(periodoEscolarRepositorio);

            ActualizarPeriodoEscolar actualizarPeriodoEscolar = new ActualizarPeriodoEscolar(buscadorPeriodoEscolar, actualizadorPeriodoEscolar);

            Date dateInicio = Date.from(fechaInicio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date dateFin = Date.from(fechaFin.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            PeriodoEscolarData periodoEscolarData = new PeriodoEscolarData(id, clave, nombre, dateInicio, dateFin, estatus);

            actualizarPeriodoEscolar.actualizar(periodoEscolarData);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Periodo escolar actualizado correctamente", ButtonType.OK);
            mensaje.setTitle("Actualización exitoso");
            mensaje.showAndWait();

            cerrarFormulario();
        } catch (ClaveDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Clave duplicada");
            mensaje.showAndWait();
        } catch (SuperposicionRangoFechasException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Superposición de fechas");
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