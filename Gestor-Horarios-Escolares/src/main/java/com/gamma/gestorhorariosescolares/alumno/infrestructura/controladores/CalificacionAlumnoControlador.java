package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.EvaluacionInscripcionData;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.RegistrarEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.buscar.BuscadorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.aplicacion.registrar.RegistradorEvaluacion;
import com.gamma.gestorhorariosescolares.evaluacion.infrestructura.persistencia.MySql2oEvaluacionRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class CalificacionAlumnoControlador {

    private final Stage stage;
    private final EvaluacionInscripcionData evaluacion;

    @FXML
    private Label txtAlumno;

    @FXML
    private TextField txtCalificacion;

    @FXML
    private Button asignarBtn;

    public CalificacionAlumnoControlador(Stage stage, EvaluacionInscripcionData evaluacion) {
        this.stage = stage;
        this.evaluacion = evaluacion;
    }

    @FXML
    public void initialize() {
        txtAlumno.setText(evaluacion.alumno().nombre() + " " + evaluacion.alumno().apellidoPaterno() + " " +
                evaluacion.alumno().apellidoMaterno());
        asignarBtn.setOnAction((actionEvent) -> guardarCalificacion());
    }

    private void guardarCalificacion() {
        Boolean validar = validarCalificacion(txtCalificacion.getText());
        if (!validar)
            return;
        asignarCalificacion(txtCalificacion.getText(), evaluacion.tipo(), evaluacion.materia().id(),
                evaluacion.alumno().id(), evaluacion.idInscripcion());
    }

    private void asignarCalificacion(String calificacion, String tipo, Integer idMateria, Integer idAlumno, Integer idInscripcion) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            var evaluacionRepositorio = new MySql2oEvaluacionRepositorio(transaccion);

            var buscadorEvaluacion = new BuscadorEvaluacion(evaluacionRepositorio);
            var registradorEvaluacion = new RegistradorEvaluacion(evaluacionRepositorio);

            RegistrarEvaluacion registrarEvaluacion = new RegistrarEvaluacion(registradorEvaluacion, buscadorEvaluacion);
            registrarEvaluacion.registrar(calificacion, tipo, idMateria, idAlumno, idInscripcion);

            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Calificacion registrada correctamente.", ButtonType.OK).showAndWait();

            cerrarFormulario();
        } catch (Sql2oException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Error al registrar en base de datos", ButtonType.OK).showAndWait();
            cerrarFormulario();
        }
    }

    private void cerrarFormulario() {
        stage.close();
    }

    private Boolean validarCalificacion(String calificacion) {
        if (calificacion.trim().length() == 0) {
            new Alert(Alert.AlertType.WARNING, "Hay campos vacios en el formulario, además no se permiten los espacios en blanco", ButtonType.OK).showAndWait();
            return false;
        }

        if (Float.parseFloat(calificacion) > 10 || Float.parseFloat(calificacion) <= 0) {
            new Alert(Alert.AlertType.WARNING, "Calificacion no valida", ButtonType.OK).showAndWait();
            return false;


        }

        return true;
    }

}