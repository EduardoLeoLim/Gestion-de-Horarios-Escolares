package com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.ActualizarMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.MaestroData;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.RegistrarMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.actualizar.ActualizadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.registrar.RegistradorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
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

public class FormularioMaestroControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final MaestroData maestro;

    @FXML
    private TextField txtNoPersonal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreoElectronico;
    @FXML
    private TextField txtClaveAcceso;
    @FXML
    private TextField txtConfirmarClaveAcceso;

    public FormularioMaestroControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.maestro = null;
    }

    public FormularioMaestroControlador(Stage stage, MaestroData maestro) {
        if (stage == null || maestro == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.maestro = maestro;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosMaestro();
    }

    private void cargarDatosMaestro() {
        if (maestro == null)
            throw new NullPointerException();
        txtNoPersonal.setText(maestro.noPersonal());
        txtNombre.setText(maestro.nombre());
        txtApellidoPaterno.setText(maestro.apellidoPaterno());
        txtApellidoMaterno.setText(maestro.apellidoMaterno());
        txtTelefono.setText(maestro.usuario().telefono());
        txtCorreoElectronico.setText(maestro.usuario().correoElectronico());
        txtClaveAcceso.setText(maestro.usuario().claveAcceso());
        txtConfirmarClaveAcceso.setText(maestro.usuario().claveAcceso());
    }

    @FXML
    private void guardarMaestro() {
        Boolean sonDatosValidos = sonValidosDatosFormulario(txtNoPersonal.getText(), txtNombre.getText(),
                txtApellidoPaterno.getText(), txtApellidoMaterno.getText(), txtTelefono.getText(),
                txtCorreoElectronico.getText(), txtClaveAcceso.getText(), txtConfirmarClaveAcceso.getText());
        if (!sonDatosValidos)
            return;

        if (esNuevoRegistro)
            registrarMaestro(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText());
        else
            actualizarMaestro(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText());
    }

    private void registrarMaestro(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                  String telefono, String correoElectronico, String claveAcceso) {
        //Conexión a base de datos
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);
            var registradorMaestro = new RegistradorMaestro(maestroRepositorio);
            var registradorUsuario = new RegistradorUsuario(usuarioRepositorio);

            RegistrarMaestro registrarMaestro = new RegistrarMaestro(buscadorMaestro, registradorMaestro, buscadorUsuario, registradorUsuario);
            registrarMaestro.registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, telefono, correoElectronico, claveAcceso);

            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Maestro registrado correctamente.", ButtonType.OK).showAndWait();

            cerrarFormulario();
        } catch (UsuarioDuplicadoException | NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (FormatoInvalidoException e) {
            Alert mensaje = new Alert(Alert.AlertType.WARNING, e.descripcion(), ButtonType.OK);
            mensaje.setTitle(e.titulo());
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Error al registrar en base de datos", ButtonType.OK).showAndWait();
            cerrarFormulario();
        }
    }

    private void actualizarMaestro(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                   String telefono, String correoElectronico, String claveAcceso) {
        if (maestro == null)
            throw new NullPointerException();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);
            var maestroRepositorio = new MySql2oMaestroRepositorio(transaccion);

            //Servicios
            var actualizadorMaestro = new ActualizadorMaestro(maestroRepositorio);
            var actualizadorUsuario = new ActualizadorUsuario(usuarioRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);
            var buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            ActualizarMaestro actualizarMaestro = new ActualizarMaestro(buscadorMaestro, actualizadorMaestro, buscadorUsuario, actualizadorUsuario);

            //Preparando datos
            UsuarioData usuarioData = new UsuarioData(maestro.usuario().id(), telefono, correoElectronico, claveAcceso, maestro.usuario().tipo());
            MaestroData maestroData = new MaestroData(maestro.id(), noPersonal, nombre, apellidoPaterno, apellidoMaterno, maestro.estatus(), usuarioData);
            actualizarMaestro.actualizar(maestroData);

            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Maestro actualizado correctamente.", ButtonType.OK).showAndWait();

            cerrarFormulario();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (UsuarioDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (FormatoInvalidoException e) {
            Alert mensaje = new Alert(Alert.AlertType.WARNING, e.descripcion(), ButtonType.OK);
            mensaje.setTitle(e.titulo());
            mensaje.showAndWait();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Error de base de datos.", ButtonType.OK).showAndWait();
            cerrarFormulario();
        }
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }

    private Boolean sonValidosDatosFormulario(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                              String telefono, String correoElectronico, String claveAcceso,
                                              String confirmacionClaveAcceso) {
        if (noPersonal.trim().length() == 0 || nombre.trim().length() == 0 || apellidoPaterno.trim().length() == 0
                || apellidoMaterno.trim().length() == 0 || telefono.trim().length() == 0 || correoElectronico.trim().length() == 0
                || claveAcceso.trim().length() == 0 || confirmacionClaveAcceso.trim().length() == 0) {
            new Alert(Alert.AlertType.WARNING, "Hay campos vacios en el formulario, además no se permiten los espacios en blanco", ButtonType.OK).showAndWait();
            return false;
        }

        if (!claveAcceso.equals(confirmacionClaveAcceso)) {
            new Alert(Alert.AlertType.WARNING, "Las contraseñas no coinciden. Asegurese que coincidan", ButtonType.OK).showAndWait();
            return false;
        }

        return true;
    }

}