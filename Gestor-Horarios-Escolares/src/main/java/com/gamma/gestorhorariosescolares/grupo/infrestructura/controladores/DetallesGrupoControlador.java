package com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseData;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.BuscarGrupos;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class DetallesGrupoControlador {

    private final Integer idGrupo;
    private final Temporizador temporizador;

    //Datos generales
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPeriodoEscolar;
    @FXML
    private TextField txtPlanEstudio;
    @FXML
    private TextField txtGrado;

    //Clases
    @FXML
    private TableView<ClaseData> tablaMaterias;

    //Alumnos
    @FXML
    private Button btnAgregarAlumno;
    @FXML
    private TableView<AlumnoData> tablaAlumnos;

    public DetallesGrupoControlador(int idGrupo) {
        this.idGrupo = idGrupo;
        temporizador = new Temporizador(1, temporizador1 -> cargarDetallesGrupo());
        temporizador.reiniciar();
    }

    private void cargarDetallesGrupo() {
        txtClave.setDisable(true);
        txtNombre.setDisable(true);
        txtPeriodoEscolar.setDisable(true);
        txtPlanEstudio.setDisable(true);
        txtGrado.setDisable(true);
        tablaMaterias.setDisable(true);
        btnAgregarAlumno.setDisable(true);
        tablaAlumnos.setDisable(true);

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();
        try (Connection transaccion = conexion.beginTransaction()) {
            cargarDatosGrupo(transaccion);
            cargarDatosClases(transaccion);
            cargarDatosAlumnos(transaccion);
        } catch (RecursoNoEncontradoException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                mensaje.setTitle("Recurso no encontrado");
                mensaje.showAndWait();
            });
        } catch (Sql2oException e) {
            Platform.runLater(() -> {
                Alert mensaje = new Alert(Alert.AlertType.ERROR, "Error de base de datos", ButtonType.OK);
                mensaje.setTitle("Error de conexión");
                mensaje.showAndWait();
            });
        } finally {
            txtClave.setDisable(false);
            txtNombre.setDisable(false);
            txtPeriodoEscolar.setDisable(false);
            txtPlanEstudio.setDisable(false);
            txtGrado.setDisable(false);
            tablaMaterias.setDisable(false);
            btnAgregarAlumno.setDisable(false);
            tablaAlumnos.setDisable(false);
        }
    }

    private void cargarDatosGrupo(Connection conexion) throws RecursoNoEncontradoException {
        //Repositorios
        var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
        var gradoRepositorio = new MySql2oGradoRepositorio(conexion);
        var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

        //Servicios
        var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
        var buscadorGrudo = new BuscadorGrado(gradoRepositorio);
        var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

        BuscarGrupos buscarGrupos = new BuscarGrupos(buscadorGrupo, buscadorGrudo, buscadorPeriodoEscolar);
        GrupoData grupo = buscarGrupos.buscarPorId(idGrupo);

        //cargarDatos en pantalla
        txtClave.setText(grupo.clave());
        txtNombre.setText(grupo.nombre());
        txtPeriodoEscolar.setText(grupo.periodoEscolar().nombre());
        //txtPlanEstudio.setText(grupo);
        txtGrado.setText(grupo.grado().nombre());
    }

    private void cargarDatosClases(Connection conexion) {

    }

    private void cargarDatosAlumnos(Connection conexion) {

    }
}
