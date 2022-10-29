package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.ActualizarSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.RegistrarSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.SecretarioData;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.actualizar.ActualizadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.buscar.BuscadorSecretario;
import com.gamma.gestorhorariosescolares.secretario.aplicacion.registrar.RegistradorSecretario;
import com.gamma.gestorhorariosescolares.secretario.dominio.SecretarioRepositorio;
import com.gamma.gestorhorariosescolares.secretario.infrestructura.persistencia.MySql2oSecretarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioSecretarioControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final SecretarioData secretario;

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

    public FormularioSecretarioControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.secretario = null;
    }

    public FormularioSecretarioControlador(Stage stage, SecretarioData secretario) {
        if (stage == null || secretario == null)
            throw new NullPointerException();
        this.stage = stage;
        esNuevoRegistro = false;
        this.secretario = secretario;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosSecretario();
    }

    private void cargarDatosSecretario() {
        if (secretario == null)
            throw new NullPointerException();

        txtNoPersonal.setText(secretario.noPersonal());
        txtNombre.setText(secretario.nombre());
        txtApellidoPaterno.setText(secretario.apellidoPaterno());
        txtApellidoMaterno.setText(secretario.apellidoMaterno());
        txtTelefono.setText(secretario.usuario().telefono());
        txtCorreoElectronico.setText(secretario.usuario().correoElectronico());
        txtClaveAcceso.setText(secretario.usuario().claveAcceso());
        txtConfirmarClaveAcceso.setText(secretario.usuario().claveAcceso());

    }

    @FXML
    private void guardarSecretario() {
        Boolean sonDatosValidos = sonValidosDatosFormulario(txtNoPersonal.getText(), txtNombre.getText(),
                txtApellidoPaterno.getText(), txtApellidoMaterno.getText(), txtTelefono.getText(),
                txtCorreoElectronico.getText(), txtClaveAcceso.getText(), txtConfirmarClaveAcceso.getText());
        if (!sonDatosValidos)
            return;

        if (esNuevoRegistro)
            registrarSecretario(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText());
        else
            actualizarSecretario(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText());
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

    public void registrarSecretario(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                    String telefono, String correoElectronico, String claveAcceso) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            SecretarioRepositorio secretarioRepositorio = new MySql2oSecretarioRepositorio(transaccion);
            UsuarioRepositorio usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            BuscadorSecretario buscadorSecretario = new BuscadorSecretario(secretarioRepositorio);
            BuscadorUsuario buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);
            RegistradorSecretario registradorSecretario = new RegistradorSecretario(secretarioRepositorio);
            RegistradorUsuario registradorUsuario = new RegistradorUsuario(usuarioRepositorio);

            new RegistrarSecretario(buscadorSecretario, registradorSecretario, buscadorUsuario, registradorUsuario)
                    .registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, telefono, correoElectronico, claveAcceso);

            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Secretario registrado correctamente.", ButtonType.OK).showAndWait();
        } catch (UsuarioDuplicadoException | NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Error al registrar en base de datos", ButtonType.OK).showAndWait();
            e.printStackTrace();
        } finally {
            cerrarFormulario();
        }
    }

    public void actualizarSecretario(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                     String telefono, String correoElectronico, String claveAcceso) {
        if (secretario == null)
            throw new NullPointerException();

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            SecretarioRepositorio secretarioRepositorio = new MySql2oSecretarioRepositorio(transaccion);
            UsuarioRepositorio usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);

            //Servicios
            BuscadorSecretario buscadorSecretario = new BuscadorSecretario(secretarioRepositorio);
            BuscadorUsuario buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);
            ActualizadorSecretario actualizadorSecretario = new ActualizadorSecretario(secretarioRepositorio);
            ActualizadorUsuario actualizadorUsuario = new ActualizadorUsuario(usuarioRepositorio);

            ActualizarSecretario actualizarSecretario = new ActualizarSecretario(buscadorSecretario, actualizadorSecretario,
                    buscadorUsuario, actualizadorUsuario);

            //Preparando datos
            UsuarioData usuarioData = new UsuarioData(secretario.usuario().id(), telefono, correoElectronico,
                    claveAcceso, secretario.usuario().tipo());
            SecretarioData secretarioData = new SecretarioData(secretario.id(), noPersonal, nombre, apellidoPaterno,
                    apellidoMaterno, secretario.estatus(), usuarioData);

            actualizarSecretario.actualizar(secretarioData);
            transaccion.commit();
            new Alert(Alert.AlertType.INFORMATION, "Secretario actualizado correctamente.", ButtonType.OK).showAndWait();

        } catch (UsuarioDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (Sql2oException e) {
            new Alert(Alert.AlertType.ERROR, "Error de base de datos.", ButtonType.OK).showAndWait();
            e.printStackTrace();
        } finally {
            cerrarFormulario();
        }
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }


}
