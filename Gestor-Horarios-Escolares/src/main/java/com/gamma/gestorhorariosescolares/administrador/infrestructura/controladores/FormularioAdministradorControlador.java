package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.administrador.aplicacion.ActualizarAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.AdministradorData;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.RegistrarAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar.ActualizadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.actualizar.ServicioActualizadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.buscar.BuscadorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar.RegistradorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.aplicacion.registrar.ServicioRegistradorAdministrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.Administrador;
import com.gamma.gestorhorariosescolares.administrador.dominio.AdministradorRepositorio;
import com.gamma.gestorhorariosescolares.administrador.infrestructura.persistencia.MySql2oAdministradorRespositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.NoPersonalDuplicadoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.servicios.ServicioBuscador;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.UsuarioData;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar.ServicioActualizadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.buscar.BuscadorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.excepciones.UsuarioDuplicadoException;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.RegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar.ServicioRegistradorUsuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.persistencia.MySql2oUsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class FormularioAdministradorControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final AdministradorData administrador;

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

    public FormularioAdministradorControlador(Stage stage) {
        this.stage = stage;
        this.esNuevoRegistro = true;
        this.administrador = null;
    }

    public FormularioAdministradorControlador(Stage stage, AdministradorData administrador) {
        if (administrador == null)
            throw new NullPointerException();
        this.stage = stage;
        this.esNuevoRegistro = false;
        this.administrador = administrador;
    }

    @FXML
    public void initialize() {
        if (!esNuevoRegistro)
            cargarDatosAdministrador();
    }

    @FXML
    private void guardarAdministrador() {
        if (esNuevoRegistro)
            registrarAdministrador(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText(), txtConfirmarClaveAcceso.getText());
        else
            actualizarAdministrador(txtNoPersonal.getText(), txtNombre.getText(), txtApellidoPaterno.getText(),
                    txtApellidoMaterno.getText(), txtTelefono.getText(), txtCorreoElectronico.getText(),
                    txtClaveAcceso.getText(), txtConfirmarClaveAcceso.getText());
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }

    private void cargarDatosAdministrador() {
        if (administrador == null)
            throw new NullPointerException();
        txtNoPersonal.setText(administrador.noPersonal());
        txtNombre.setText(administrador.nombre());
        txtApellidoPaterno.setText(administrador.apellidoPaterno());
        txtApellidoMaterno.setText(administrador.apellidoMaterno());
        txtTelefono.setText(administrador.usuario().telefono());
        txtCorreoElectronico.setText(administrador.usuario().correoElectronico());
        txtClaveAcceso.setText(administrador.usuario().claveAcceso());
        txtConfirmarClaveAcceso.setText(administrador.usuario().claveAcceso());
    }

    public void registrarAdministrador(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                       String telefono, String correoElectronico, String claveAcceso, String confirmacionClaveAcceso) {
        Boolean sonDatosValidos = sonValidosDatosFormulario(noPersonal, nombre, apellidoPaterno, apellidoMaterno, telefono,
                correoElectronico, claveAcceso, confirmacionClaveAcceso);
        if (!sonDatosValidos)
            return;

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            UsuarioRepositorio usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);
            AdministradorRepositorio administradorRepositorio = new MySql2oAdministradorRespositorio(transaccion);

            ServicioBuscador<Administrador> buscadorAdministrador = new BuscadorAdministrador(administradorRepositorio);
            ServicioBuscador<Usuario> buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            ServicioRegistradorAdministrador registradorAdministrador = new RegistradorAdministrador(administradorRepositorio);
            ServicioRegistradorUsuario registradorUsuario = new RegistradorUsuario(usuarioRepositorio);

            new RegistrarAdministrador(buscadorAdministrador, registradorAdministrador, buscadorUsuario, registradorUsuario)
                    .registrar(noPersonal, nombre, apellidoPaterno, apellidoMaterno, telefono, correoElectronico, claveAcceso);

            transaccion.commit();
        } catch (UsuarioDuplicadoException | NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        }
    }

    public void actualizarAdministrador(String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno,
                                        String telefono, String correoElectronico, String claveAcceso, String confirmacionClaveAcceso) {
        if (administrador == null)
            throw new NullPointerException();

        Boolean sonDatosValidos = sonValidosDatosFormulario(noPersonal, nombre, apellidoPaterno, apellidoMaterno, telefono,
                correoElectronico, claveAcceso, confirmacionClaveAcceso);
        if (!sonDatosValidos)
            return;

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            UsuarioRepositorio usuarioRepositorio = new MySql2oUsuarioRepositorio(transaccion);
            AdministradorRepositorio administradorRepositorio = new MySql2oAdministradorRespositorio(transaccion);

            ServicioActualizadorAdministrador actualizadorAdministrador = new ActualizadorAdministrador(administradorRepositorio);
            ServicioActualizadorUsuario actualizadorUsuario = new ActualizadorUsuario(usuarioRepositorio);
            ServicioBuscador<Administrador> buscadorAdministrador = new BuscadorAdministrador(administradorRepositorio);
            ServicioBuscador<Usuario> buscadorUsuario = new BuscadorUsuario(usuarioRepositorio);

            ActualizarAdministrador actualizarAdministrador = new ActualizarAdministrador(actualizadorAdministrador,
                    buscadorAdministrador, buscadorUsuario, actualizadorUsuario);

            //Preparando datos
            UsuarioData usuarioData = new UsuarioData(administrador.usuario().id(), telefono, correoElectronico, claveAcceso, administrador.usuario().tipo());
            AdministradorData administradorData = new AdministradorData(administrador.id(), noPersonal, nombre, apellidoPaterno, apellidoMaterno, administrador.estatus(), usuarioData);

            actualizarAdministrador.actualizar(administradorData);
        } catch (RecursoNoEncontradoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (UsuarioDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (NoPersonalDuplicadoException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
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

        if (!claveAcceso.trim().equals(confirmacionClaveAcceso.trim())) {
            new Alert(Alert.AlertType.WARNING, "Las contraseñas no coinciden. Asegurese que coincidan", ButtonType.OK).showAndWait();
            return false;
        }

        return true;
    }

}