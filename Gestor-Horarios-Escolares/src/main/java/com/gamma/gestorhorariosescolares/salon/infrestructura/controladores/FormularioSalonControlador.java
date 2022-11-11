package com.gamma.gestorhorariosescolares.salon.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.ClaveDuplicadaException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.BuscarEdificios;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificiosData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.ActualizarSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.RegistrarSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.actualizar.ActualizadorSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.buscar.BuscadorSalon;
import com.gamma.gestorhorariosescolares.salon.aplicacion.registrar.RegistradorSalon;
import com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia.MySql2oSalonRepositorio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioSalonControlador {

    private final Stage stage;
    private final boolean esNuevoRegistro;
    private final SalonData salon;

    @FXML
    private ComboBox<EdificioData> cbxEdificio;
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtCapacidad;

    public FormularioSalonControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();
        this.stage = stage;
        this.salon = null;
        this.esNuevoRegistro = true;
    }

    public FormularioSalonControlador(Stage stage, SalonData salon) {
        if (stage == null && salon == null)
            throw new NullPointerException();
        this.stage = stage;
        this.salon = salon;
        this.esNuevoRegistro = false;
    }

    @FXML
    public void initialize() {
        cargarComboBoxEdificios();

        if (!esNuevoRegistro)
            cargarDatosSecretario();
    }

    private void cargarDatosSecretario() {
        EdificioData edificioSeleccion = cbxEdificio.getItems().stream()
                .filter(edificio -> edificio.id().equals(this.salon.edificio().id()))
                .findFirst()
                .orElse(null);

        if (edificioSeleccion != null)
            cbxEdificio.getSelectionModel().select(edificioSeleccion);

        txtClave.setText(salon.clave());
        txtCapacidad.setText(salon.capacidad().toString());
    }

    private void cargarComboBoxEdificios() {
        cbxEdificio.getItems().clear();

        cbxEdificio.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(EdificioData item, boolean empty) {
                super.updateItem(item, empty);
                setText("");
                if (empty || item == null)
                    return;

                setText(item.clave() + " - " + item.nombre());
            }
        });

        cbxEdificio.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(EdificioData item, boolean empty) {
                super.updateItem(item, empty);
                setText("");
                if (empty || item == null)
                    return;

                setText(item.clave() + " - " + item.nombre());
            }
        });

        EdificiosData edificios;

        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarEdificios buscarEdificios = new BuscarEdificios(buscadorEdificio);

            edificios = buscarEdificios.buscarTodos();
            cbxEdificio.getItems().addAll(edificios.edificios());

        } catch (Sql2oException e) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Base de datos no disponible", ButtonType.OK);
            mensaje.setTitle("Error base de datos");
            mensaje.showAndWait();
        }
    }

    @FXML
    private void guardarSalon() {
        EdificioData edificioSeleccionado = cbxEdificio.getSelectionModel().getSelectedItem();
        String clave = txtClave.getText();
        String capacidad = txtCapacidad.getText();

        if (!sonValidosDatosFormulario(edificioSeleccionado, clave, capacidad))
            return;

        if (esNuevoRegistro)
            registrarSalon(clave, Integer.parseInt(capacidad), edificioSeleccionado.id());
        else
            actualizarSalon(this.salon.id(), clave, Integer.parseInt(capacidad), this.salon.estatus(), edificioSeleccionado);
    }

    private Boolean sonValidosDatosFormulario(EdificioData edificioSeleccionado, String clave, String capacidad) {
        if (clave.isBlank() || capacidad.isBlank() || edificioSeleccionado == null) {
            Alert mensaje = new Alert(Alert.AlertType.ERROR, "Hay campos vacios en el formulario", ButtonType.OK);
            mensaje.setTitle("Campos vacíos");
            mensaje.showAndWait();
            return false;
        }

        return true;
    }

    private void registrarSalon(String clave, int capacidad, int idEdificio) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);
            var salonRepositorio = new MySql2oSalonRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var registradorSalon = new RegistradorSalon(salonRepositorio);

            RegistrarSalon registrarSalon = new RegistrarSalon(buscadorEdificio, buscadorSalon, registradorSalon);
            registrarSalon.registrar(clave, capacidad, idEdificio);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Salón registrado correctamente", ButtonType.OK);
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

    private void actualizarSalon(int id, String clave, int capacidad, boolean estatus, EdificioData edificio) {
        Sql2o conexion = MySql2oConexiones.getConexionPrimaria();

        try (Connection transaccion = conexion.beginTransaction()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(transaccion);
            var salonRepositorio = new MySql2oSalonRepositorio(transaccion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var actualizadorSalon = new ActualizadorSalon(salonRepositorio);

            ActualizarSalon actualizarSalon = new ActualizarSalon(buscadorEdificio, buscadorSalon, actualizadorSalon);

            SalonData salonData = new SalonData(id, clave, capacidad, estatus, edificio);

            actualizarSalon.actualizar(salonData);

            transaccion.commit();

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION, "Salón actualizado correctamente", ButtonType.OK);
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
