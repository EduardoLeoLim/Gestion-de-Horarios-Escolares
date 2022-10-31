package com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.alumno.aplicacion.ActualizarAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.AlumnoData;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.RegistrarAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.actualizar.ActualizadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.buscar.BuscadorAlumno;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.CurpDuplicadoException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.excepciones.MatriculaDuplicadaException;
import com.gamma.gestorhorariosescolares.alumno.aplicacion.registrar.RegistradorAlumno;
import com.gamma.gestorhorariosescolares.alumno.infrestructura.persistencia.MySql2oAlumnoRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioAlumnoControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final AlumnoData alumno;

    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtCurp;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreoElectronico;
    @FXML
    private TextField txtClaveAcceso;
    @FXML
    private TextField txtConfirmarClaveAcceso;

    public FormularioAlumnoControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.alumno = null;
    }

    public FormularioAlumnoControlador(Stage stage, AlumnoData alumno) {
        if (stage == null || alumno == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.alumno = alumno;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosAlumno();
    }

    private void cargarDatosAlumno() {
        if (alumno == null)
            throw new NullPointerException();

        txtMatricula.setText(alumno.matricula());
        txtNombre.setText(alumno.nombre());
        txtApellidoPaterno.setText(alumno.apellidoPaterno());
        txtApellidoMaterno.setText(alumno.apellidoMaterno());
        txtCurp.setText(alumno.curp());
        txtTelefono.setText(alumno.usuario().telefono());
        txtCorreoElectronico.setText(alumno.usuario().correoElectronico());
        txtClaveAcceso.setText(alumno.usuario().claveAcceso());
        txtConfirmarClaveAcceso.setText(alumno.usuario().claveAcceso());
    }

    @FXML
    protected void guardarAlumno() {
        String matricula = txtMatricula.getText();
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String curp = txtCurp.getText();
        String telefono = txtTelefono.getText();
        String correoElectronico = txtCorreoElectronico.getText();
        String claveAcceso = txtClaveAcceso.getText();
        String confirmarClaveAcceso = txtConfirmarClaveAcceso.getText();

        Boolean sonDatosValidos = sonValidosDatosFormulario(matricula, nombre, apellidoPaterno, apellidoMaterno, curp,
                telefono, correoElectronico, claveAcceso, confirmarClaveAcceso);
        if (!sonDatosValidos)
            return;

        if (esNuevoRegistro)
            registrarAlumno(matricula, nombre, apellidoPaterno, apellidoMaterno, curp, telefono, correoElectronico,
                    claveAcceso);
        else
            actualizarAlumno(matricula, nombre, apellidoPaterno, apellidoMaterno, curp, telefono, correoElectronico,
                    claveAcceso);
    }

    private Boolean sonValidosDatosFormulario(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno,
                                              String curp, String telefono, String correoElectronico, String claveAcceso,
                                              String confirmacionClaveAcceso) {
        if (matricula.isBlank() || nombre.isBlank() || apellidoPaterno.isBlank() || apellidoMaterno.isBlank()
                || curp.isBlank() || telefono.isBlank() || correoElectronico.isBlank() || claveAcceso.isBlank()
                || confirmacionClaveAcceso.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Hay campos vacios en el formulario, además no se permiten los espacios en blanco", ButtonType.OK).showAndWait();
            return false;
        }

        if (!claveAcceso.equals(confirmacionClaveAcceso)) {
            new Alert(Alert.AlertType.WARNING, "Las contraseñas no coinciden. Asegurese que coincidan", ButtonType.OK).showAndWait();
            return false;
        }

        return true;
    }

    private void registrarAlumno(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String curp,
                                 String telefono, String correoElectronico, String claveAcceso) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(transaccion);

            //Servicios
            var registradorAlumno = new RegistradorAlumno(alumnoRepositorio);
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var registradorUsuario = new RegistradorUsuario(usuarioRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            new RegistrarAlumno(registradorAlumno, buscadorAlumno, registradorUsuario, buscadorUsuario)
                    .registrar(nombre, apellidoPaterno, apellidoMaterno, curp, matricula, telefono, correoElectronico, claveAcceso);

            transaccion.commit();//Guardar cambios en base de datos
            new Alert(Alert.AlertType.INFORMATION, "Alumno registrado correctamente", ButtonType.OK)
                    .showAndWait();

            cerrarFormulario();
        } catch (CurpDuplicadoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("CURP inválido");
            mensaje.showAndWait();
        } catch (UsuarioDuplicadoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Usuario no disponible");
            mensaje.showAndWait();
        } catch (MatriculaDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Matrícula inválida");
            mensaje.showAndWait();
        } catch (FormatoInvalidoException e) {
            Alert mensaje = new Alert(Alert.AlertType.WARNING, e.descripcion(), ButtonType.OK);
            mensaje.setTitle(e.titulo());
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensajeError = new Alert(Alert.AlertType.ERROR, "Error al registrar en base de datos. Intentalo más tarde", ButtonType.OK);
            mensajeError.setTitle("Error de base de datos");
            mensajeError.showAndWait();
            cerrarFormulario();
        }
    }

    public void actualizarAlumno(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String curp,
                                 String telefono, String correoElectronico, String claveAcceso) {
        if (alumno == null)
            throw new NullPointerException();
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var alumnoRepositorio = new MySql2oAlumnoRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            var actualizadorAlumno = new ActualizadorAlumno(alumnoRepositorio);
            var buscadorAlumno = new BuscadorAlumno(alumnoRepositorio);
            var actualizadorUsuario = new ActualizadorUsuario(usuarioRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            ActualizarAlumno actualizarAlumno = new ActualizarAlumno(actualizadorAlumno, buscadorAlumno,
                    actualizadorUsuario, buscadorUsuario);

            UsuarioData usuarioData = new UsuarioData(alumno.usuario().id(), telefono, correoElectronico, claveAcceso,
                    alumno.usuario().tipo());
            AlumnoData alumnoData = new AlumnoData(alumno.id(), matricula, curp, nombre, apellidoPaterno, apellidoMaterno,
                    alumno.estatus(), usuarioData);

            actualizarAlumno.actualizar(alumnoData);

            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Alumno actualizado correctamente", ButtonType.OK)
                    .showAndWait();

            cerrarFormulario();
        } catch (CurpDuplicadoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("CURP inválido");
            mensaje.showAndWait();
        } catch (UsuarioDuplicadoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Usuario no disponible");
            mensaje.showAndWait();
        } catch (RecursoNoEncontradoException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Recurso no encontrado");
            mensaje.showAndWait();
        } catch (MatriculaDuplicadaException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            mensaje.setTitle("Matrícula inválida");
            mensaje.showAndWait();
        } catch (FormatoInvalidoException e) {
            Alert mensaje = new Alert(Alert.AlertType.WARNING, e.descripcion(), ButtonType.OK);
            mensaje.setTitle(e.titulo());
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            Alert mensajeError = new Alert(Alert.AlertType.ERROR, "Error al registrar en base de datos. Intentalo más tarde", ButtonType.OK);
            mensajeError.setTitle("Error de base de datos");
            mensajeError.showAndWait();
            cerrarFormulario();
        }
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }
}