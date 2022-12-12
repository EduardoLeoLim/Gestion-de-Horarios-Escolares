package com.gamma.gestorhorariosescolares.horario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.BuscarClasesPorGrupo;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClasesGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RegistroHorarioInvalidoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.BuscarGrupos;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GruposData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorarioDisponibleData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.RegistrarHorario;
import com.gamma.gestorhorariosescolares.horario.aplicacion.buscar.BuscadorHorario;
import com.gamma.gestorhorariosescolares.horario.aplicacion.registrar.RegistradorHorario;
import com.gamma.gestorhorariosescolares.horario.infrestructura.persistencia.MySql2oHorarioRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.buscar.BuscadorSalon;
import com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia.MySql2oSalonRepositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class FormularioRegistrarHorarioClaseControlador {

    private final HorarioDisponibleData horarioDisponible;
    private final Stage stage;
    private ObservableList<GrupoData> coleccionGrupos;
    private ObservableList<ClaseGrupoData> coleccionClassesGrupo;

    @FXML
    private Label lblEdificio;
    @FXML
    private Label lblSalon;
    @FXML
    private Label lblPeriodoEscolar;
    @FXML
    private Label lblHoraInicio;
    @FXML
    private Label lblHoraFin;
    @FXML
    private Label lblDia;
    @FXML
    private ComboBox<GrupoData> cbxGrupo;
    @FXML
    private ComboBox<ClaseGrupoData> cbxMateria;

    public FormularioRegistrarHorarioClaseControlador(Stage stage, HorarioDisponibleData horarioDisponible) {
        if (stage == null)
            throw new IllegalArgumentException("Stage no puede ser nulo");
        if (horarioDisponible == null)
            throw new NullPointerException("El horario disponible no puede ser nulo");

        this.stage = stage;
        this.horarioDisponible = horarioDisponible;
    }

    @FXML
    private void initialize() {
        inicializarCbxGrupo();
        inicializarCbxMateria();

        cargarGrupos();
        cargarDatosHorarioDisponible();
    }

    private void inicializarCbxGrupo() {
        coleccionGrupos = FXCollections.observableArrayList();
        cbxGrupo.setItems(coleccionGrupos);

        cbxGrupo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GrupoData grupo, boolean empty) {
                super.updateItem(grupo, empty);
                setText("");
                if (empty)
                    return;

                setText(grupo.clave() + " - " + grupo.nombre());
            }
        });

        cbxGrupo.setCellFactory((ListView) -> new ListCell<>() {
            @Override
            protected void updateItem(GrupoData grupo, boolean empty) {
                super.updateItem(grupo, empty);
                setText("");
                if (empty)
                    return;

                setText(grupo.clave() + " - " + grupo.nombre());
            }
        });

        cbxGrupo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                cargarMaterias(newValue);
        });
    }

    private void cargarGrupos() {
        coleccionGrupos.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
            var gradoRepositorio = new MySql2oGradoRepositorio(conexion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarGrupos buscarGrupos = new BuscarGrupos(buscadorGrupo, buscadorGrado, buscadorPeriodoEscolar);

            GruposData grupos = buscarGrupos.buscarPorIdPeriodoEscolar(horarioDisponible.periodoEscolar().id());
            coleccionGrupos.addAll(grupos.grupos());
        } catch (RecursoNoEncontradoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los grupos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los grupos");
            alert.setContentText("Error al cargar los grupos");
            alert.showAndWait();
        }
    }

    private void inicializarCbxMateria() {
        coleccionClassesGrupo = FXCollections.observableArrayList();
        cbxMateria.setItems(coleccionClassesGrupo);

        cbxMateria.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ClaseGrupoData claseGrupo, boolean empty) {
                super.updateItem(claseGrupo, empty);
                setText("");
                if (empty)
                    return;

                setText(claseGrupo.materia().nombre());
            }
        });

        cbxMateria.setCellFactory((ListView) -> new ListCell<>() {
            @Override
            protected void updateItem(ClaseGrupoData claseGrupo, boolean empty) {
                super.updateItem(claseGrupo, empty);
                setText("");
                if (empty)
                    return;

                setText(claseGrupo.materia().nombre());
            }
        });

    }

    private void cargarMaterias(GrupoData grupo) {
        coleccionClassesGrupo.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
            var claseRepositorio = new MySql2oClaseRepositorio(conexion);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexion);
            var maestroRepositorio = new MySql2oMaestroRepositorio(conexion);

            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);

            BuscarClasesPorGrupo buscarClasesPorGrupo = new BuscarClasesPorGrupo(buscadorGrupo, buscadorClase,
                    buscadorMateria, buscadorMaestro);

            ClasesGrupoData clasesGrupo = buscarClasesPorGrupo.buscar(grupo.id());
            coleccionClassesGrupo.addAll(clasesGrupo.clases());
        } catch (RecursoNoEncontradoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar las materias");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar las materias");
            alert.setContentText("Error al cargar las materias");
            alert.showAndWait();
        }
    }

    private void cargarDatosHorarioDisponible() {
        EdificioData edificio = horarioDisponible.salon().edificio();
        lblEdificio.setText(edificio.clave() + " - " + edificio.nombre());

        lblSalon.setText(horarioDisponible.salon().clave());

        PeriodoEscolarData periodoEscolar = horarioDisponible.periodoEscolar();
        lblPeriodoEscolar.setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());

        lblHoraInicio.setText(horarioDisponible.horaInicio().toString());
        lblHoraFin.setText(horarioDisponible.horaFin().toString());

        int diaSemana = horarioDisponible.diaSemana();

        switch (diaSemana) {
            case 0 -> lblDia.setText("Lunes");
            case 1 -> lblDia.setText("Martes");
            case 2 -> lblDia.setText("Miércoles");
            case 3 -> lblDia.setText("Jueves");
            case 4 -> lblDia.setText("Viernes");
            default -> lblDia.setText("");
        }

    }

    @FXML
    private void guardarHorario() {
        if (validarCampos()) {
            Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

            try (Connection conexion = conexionSql2o.beginTransaction()) {
                var horarioRepositorio = new MySql2oHorarioRepositorio(conexion);
                var claseRepositorio = new MySql2oClaseRepositorio(conexion);
                var salonRepositorio = new MySql2oSalonRepositorio(conexion);
                var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);

                var buscadorHorario = new BuscadorHorario(horarioRepositorio);
                var buscadorClase = new BuscadorClase(claseRepositorio);
                var buscadorSalon = new BuscadorSalon(salonRepositorio);
                var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
                var registradorHorario = new RegistradorHorario(horarioRepositorio);

                RegistrarHorario registrarHorario = new RegistrarHorario(buscadorHorario, buscadorClase, buscadorSalon,
                        buscadorGrupo, registradorHorario);

                registrarHorario.registrar(horarioDisponible.diaSemana(), horarioDisponible.horaInicio(), horarioDisponible.horaFin(), cbxMateria.getValue().id(), horarioDisponible.salon().id());

                conexion.commit();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Horario creado");
                alert.setContentText("El horario se ha creado correctamente");
                alert.showAndWait();

                stage.close();
            } catch (RecursoNoEncontradoException | RegistroHorarioInvalidoException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al crear el horario");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (Sql2oException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al crear el horario");
                alert.setContentText("Error al crear el horario");
                alert.showAndWait();
            }
        }
    }

    private boolean validarCampos() {
        if (cbxMateria.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al crear el horario");
            alert.setContentText("Debe seleccionar una clase");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    private void cerrarFormulario() {
        stage.close();
    }
}
