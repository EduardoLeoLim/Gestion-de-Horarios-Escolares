package com.gamma.gestorhorariosescolares.horario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.BuscarClasesPorGrupo;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClaseGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.ClasesGrupoData;
import com.gamma.gestorhorariosescolares.clase.aplicacion.buscar.BuscadorClase;
import com.gamma.gestorhorariosescolares.clase.infrestructura.persistencia.MySql2oClaseRepositorio;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.BuscarEdificios;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificiosData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import com.gamma.gestorhorariosescolares.grado.aplicacion.buscar.BuscadorGrado;
import com.gamma.gestorhorariosescolares.grado.infrestructura.persistencia.MySql2oGradoRepositorio;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.BuscarGrupos;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GrupoData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.GruposData;
import com.gamma.gestorhorariosescolares.grupo.aplicacion.buscar.BuscadorGrupo;
import com.gamma.gestorhorariosescolares.grupo.infrestructura.persistencia.MySql2oGrupoRepositorio;
import com.gamma.gestorhorariosescolares.horario.aplicacion.BuscarHorariosOcupados;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorarioData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorariosData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.buscar.BuscadorHorario;
import com.gamma.gestorhorariosescolares.horario.infrestructura.persistencia.MySql2oHorarioRepositorio;
import com.gamma.gestorhorariosescolares.maestro.aplicacion.buscar.BuscadorMaestro;
import com.gamma.gestorhorariosescolares.maestro.infrestructura.persistencia.MySql2oMaestroRepositorio;
import com.gamma.gestorhorariosescolares.materia.aplicacion.buscar.BuscadorMateria;
import com.gamma.gestorhorariosescolares.materia.infrestructura.persistencia.MySql2oMateriaRepositorio;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.BuscarPeriodosEscolares;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodoEscolarData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.PeriodosEscolaresData;
import com.gamma.gestorhorariosescolares.periodoescolar.aplicacion.buscar.BuscadorPeriodoEscolar;
import com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.persistencia.MySql2oPeriodoEscolarRepositorio;
import com.gamma.gestorhorariosescolares.salon.aplicacion.BuscarSalones;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.SalonesData;
import com.gamma.gestorhorariosescolares.salon.aplicacion.buscar.BuscadorSalon;
import com.gamma.gestorhorariosescolares.salon.infrestructura.persistencia.MySql2oSalonRepositorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarHorariosSecretario {

    private final Stage stage;
    private final Map<SalonData, List<HorarioData>> horariosPorSalon;
    private ObservableList<PeriodoEscolarData> coleccionPeriodosEscolares;
    private ObservableList<EdificioData> coleccionEdificios;
    private ObservableList<SalonData> coleccionSalones;
    private ObservableList<GrupoData> coleccionGrupos;
    private ObservableList<ClaseGrupoData> coleccionClases;
    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private ComboBox<EdificioData> cbxEdificio;
    @FXML
    private ComboBox<SalonData> cbxSalon;
    @FXML
    private ComboBox<GrupoData> cbxGrupo;
    @FXML
    private ComboBox<ClaseGrupoData> cbxClase;
    @FXML
    private VBox panelHorarios;

    public ConsultarHorariosSecretario(Stage stage) {
        this.stage = stage;
        horariosPorSalon = new HashMap<>();
    }

    @FXML
    private void initialize() {
        inicializarComboBoxPeriodoEscolar();
        inicializarComboBoxEdificio();
        inicializarComboBoxSalon();
        inicializarComboBoxGrupo();
        inicializarComboBoxClase();

        cargarDatosSelectores();
    }

    private void cargarDatosSelectores() {
        coleccionPeriodosEscolares.clear();
        coleccionEdificios.clear();
        coleccionSalones.clear();
        coleccionGrupos.clear();
        coleccionClases.clear();

        cargarPeriodosEscolares();
        cargarEdificios();


    }

    private void inicializarComboBoxPeriodoEscolar() {
        coleccionPeriodosEscolares = FXCollections.observableArrayList();
        cbxPeriodoEscolar.setItems(coleccionPeriodosEscolares);

        cbxPeriodoEscolar.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                setText("");
                if (empty)
                    return;
                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });

        cbxPeriodoEscolar.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(PeriodoEscolarData periodoEscolar, boolean empty) {
                super.updateItem(periodoEscolar, empty);
                setText("");
                if (empty)
                    return;
                setText(periodoEscolar.clave() + " - " + periodoEscolar.nombre());
            }
        });

        cbxPeriodoEscolar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            cargarGrupos(newValue);
        });
    }

    private void cargarPeriodosEscolares() {
        coleccionPeriodosEscolares.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);
            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarTodos();

            coleccionPeriodosEscolares.addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los periodos escolares");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarComboBoxEdificio() {
        coleccionEdificios = FXCollections.observableArrayList();
        cbxEdificio.setItems(coleccionEdificios);

        cbxEdificio.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(EdificioData edificio, boolean empty) {
                super.updateItem(edificio, empty);
                setText("");
                if (empty)
                    return;
                setText(edificio.clave() + " - " + edificio.nombre());
            }
        });

        cbxEdificio.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(EdificioData edificio, boolean empty) {
                super.updateItem(edificio, empty);
                setText("");
                if (empty)
                    return;
                setText(edificio.clave() + " - " + edificio.nombre());
            }
        });

        cbxEdificio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            cargarSalones(newValue);
        });
    }

    private void cargarEdificios() {
        coleccionEdificios.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(conexion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarEdificios buscarEdificios = new BuscarEdificios(buscadorEdificio);
            EdificiosData edificios = buscarEdificios.buscarHabilitados();

            coleccionEdificios.addAll(edificios.edificios());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los edificios");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarComboBoxSalon() {
        coleccionSalones = FXCollections.observableArrayList();
        cbxSalon.setItems(coleccionSalones);

        cbxSalon.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(SalonData salon, boolean empty) {
                super.updateItem(salon, empty);
                setText("");
                if (empty)
                    return;
                setText(salon.clave());
            }
        });

        cbxSalon.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(SalonData salon, boolean empty) {
                super.updateItem(salon, empty);
                setText("");
                if (empty)
                    return;
                setText(salon.clave());
            }
        });
    }

    private void cargarSalones(EdificioData edificio) {
        coleccionSalones.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var salonRepositorio = new MySql2oSalonRepositorio(conexion);
            var edificioRepositorio = new MySql2oEdificioRepositorio(conexion);

            //Servicios
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarSalones buscarSalones = new BuscarSalones(buscadorSalon, buscadorEdificio);
            SalonesData salones = buscarSalones.buscarSalonesDeEdificio(edificio.id());

            coleccionSalones.addAll(salones.salones());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los salones");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarComboBoxGrupo() {
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

        cbxGrupo.setCellFactory(listView -> new ListCell<>() {
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
            if (newValue == null)
                return;
            cargarClases(newValue);
        });
    }

    private void cargarGrupos(PeriodoEscolarData periodoEscolar) {
        coleccionGrupos.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
            var gradoRepositorio = new MySql2oGradoRepositorio(conexion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

            //Servicios
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorGrado = new BuscadorGrado(gradoRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarGrupos buscarGrupos = new BuscarGrupos(buscadorGrupo, buscadorGrado, buscadorPeriodoEscolar);
            GruposData grupos = buscarGrupos.buscarPorIdPeriodoEscolar(periodoEscolar.id());

            coleccionGrupos.addAll(grupos.grupos());
        } catch (Sql2oException | RecursoNoEncontradoException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los grupos");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarComboBoxClase() {
        coleccionClases = FXCollections.observableArrayList();
        cbxClase.setItems(coleccionClases);

        cbxClase.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ClaseGrupoData clase, boolean empty) {
                super.updateItem(clase, empty);
                setText("");
                if (empty)
                    return;
                setText(clase.materia().clave() + " - " + clase.materia().nombre());
            }
        });

        cbxClase.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(ClaseGrupoData clase, boolean empty) {
                super.updateItem(clase, empty);
                setText("");
                if (empty)
                    return;
                setText(clase.materia().clave() + " - " + clase.materia().nombre());
            }
        });
    }

    private void cargarClases(GrupoData grupo) {
        coleccionClases.clear();

        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var claseRepositorio = new MySql2oClaseRepositorio(conexion);
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexion);
            var maestroRepositorio = new MySql2oMaestroRepositorio(conexion);

            //Servicios
            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);

            BuscarClasesPorGrupo buscarClasesGrupo = new BuscarClasesPorGrupo(buscadorGrupo, buscadorClase, buscadorMateria, buscadorMaestro);
            ClasesGrupoData clases = buscarClasesGrupo.buscar(grupo.id());

            coleccionClases.addAll(clases.clases());
        } catch (Sql2oException | RecursoNoEncontradoException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar las clases");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarHorariosOcupadosClick() {
        PeriodoEscolarData periodoEscolar = cbxPeriodoEscolar.getValue();
        GrupoData grupo = cbxGrupo.getValue();
        ClaseGrupoData clase = cbxClase.getValue();
        EdificioData edificio = cbxEdificio.getValue();
        SalonData salon = cbxSalon.getValue();

        if (sonValidosDatosSeleccionados(periodoEscolar, edificio)) {
            List<SalonData> salones = new ArrayList<>();
            if (salon == null)
                salones.addAll(coleccionSalones);
            else
                salones.add(salon);

            List<GrupoData> grupos = new ArrayList<>();
            if (grupo == null)
                grupos.addAll(coleccionGrupos);
            else
                grupos.add(grupo);

            List<ClaseGrupoData> clases = new ArrayList<>();
            if (clase == null)
                clases.addAll(coleccionClases);
            else
                clases.add(clase);

            cargarHorariosOcupados(periodoEscolar, salones, grupos, clases);
            cargarDatosSelectores();
        }
    }

    private boolean sonValidosDatosSeleccionados(PeriodoEscolarData periodoEscolar, EdificioData edificio) {
        if (periodoEscolar == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al buscar horarios ocupados");
            alert.setContentText("Debe seleccionar un periodo escolar");
            alert.showAndWait();
            return false;
        }
        if (edificio == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al buscar horarios ocupados");
            alert.setContentText("Debe seleccionar un edificio");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void cargarHorariosOcupados(PeriodoEscolarData periodoEscolar, List<SalonData> salones,
                                        List<GrupoData> grupos, List<ClaseGrupoData> clases) {
        horariosPorSalon.clear();
        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var horarioRepositorio = new MySql2oHorarioRepositorio(conexion);
            var salonRepositorio = new MySql2oSalonRepositorio(conexion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);
            var grupoRepositorio = new MySql2oGrupoRepositorio(conexion);
            var claseRepositorio = new MySql2oClaseRepositorio(conexion);
            var materiaRepositorio = new MySql2oMateriaRepositorio(conexion);
            var maestroRepositorio = new MySql2oMaestroRepositorio(conexion);

            //Servicios
            var buscadorHorario = new BuscadorHorario(horarioRepositorio);
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);
            var buscadorGrupo = new BuscadorGrupo(grupoRepositorio);
            var buscadorClase = new BuscadorClase(claseRepositorio);
            var buscadorMateria = new BuscadorMateria(materiaRepositorio);
            var buscadorMaestro = new BuscadorMaestro(maestroRepositorio);

            BuscarHorariosOcupados buscarHorariosOcupados = new BuscarHorariosOcupados(buscadorHorario, buscadorPeriodoEscolar,
                    buscadorSalon, buscadorGrupo, buscadorClase, buscadorMateria, buscadorMaestro);

            if (grupos.isEmpty())
                grupos.add(null);
            if (clases.isEmpty())
                clases.add(null);

            for (GrupoData grupo : grupos) {
                for (SalonData salon : salones) {
                    for (ClaseGrupoData clase : clases) {
                        Integer idGrupo = grupo == null ? null : grupo.id();
                        Integer idClase = clase == null ? null : clase.id();

                        HorariosData horarios = buscarHorariosOcupados.buscar(periodoEscolar.id(), salon.id(), idGrupo, idClase);
                        agregarHorarios(salon, horarios.horarios());
                    }
                }
            }

            cargarHorario();
        } catch (RecursoNoEncontradoException | Sql2oException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al buscar horarios ocupados");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void agregarHorarios(SalonData salon, List<HorarioData> horarios) {
        boolean existeSalon = false;

        for (Map.Entry<SalonData, List<HorarioData>> entry : horariosPorSalon.entrySet()) {
            SalonData salonKey = entry.getKey();
            if (salonKey.id().equals(salon.id())) {
                existeSalon = true;
                List<HorarioData> horariosSalon = entry.getValue();
                for (HorarioData horario : horarios) {
                    horariosSalon.stream()
                            .filter(h -> h.id() == horario.id())
                            .findFirst().ifPresentOrElse(
                                    h -> {
                                    },
                                    () -> horariosSalon.add(horario)
                            );
                    horariosPorSalon.replace(salonKey, horariosSalon);
                }
                break;
            }
        }

        if (!existeSalon)
            horariosPorSalon.put(salon, horarios);
    }

    private void cargarHorario() {
        panelHorarios.getChildren().clear();
        for (Map.Entry<SalonData, List<HorarioData>> entry : horariosPorSalon.entrySet()) {
            SalonData salon = entry.getKey();
            List<HorarioData> horarios = entry.getValue();

            //Título
            Label lblTitulo = new Label();
            lblTitulo.setText(salon.clave() + " - " + salon.edificio().nombre());
            lblTitulo.getStyleClass().addAll("h5", "b");
            lblTitulo.setPadding(new Insets(15, 0, 5, 0));

            GridPane calendario = new GridPane();
            calendario.getStyleClass().addAll("h4");
            calendario.setGridLinesVisible(true);
            final int numColumnas = 6;
            final int numFilas = 16;
            for (int i = 0; i < numColumnas; i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / numColumnas);
                calendario.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i < numFilas; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / numFilas);
                calendario.getRowConstraints().add(rowConst);
            }
            calendario.add(new Label("Lunes"), 1, 0);
            calendario.add(new Label("Martes"), 2, 0);
            calendario.add(new Label("Miércoles"), 3, 0);
            calendario.add(new Label("Jueves"), 4, 0);
            calendario.add(new Label("Viernes"), 5, 0);

            calendario.add(new Label("7:00 - 7:59"), 0, 1);
            calendario.add(new Label("8:00 - 8:59"), 0, 2);
            calendario.add(new Label("9:00 - 9:59"), 0, 3);
            calendario.add(new Label("10:00 - 10:59"), 0, 4);
            calendario.add(new Label("11:00 - 11:59"), 0, 5);
            calendario.add(new Label("12:00 - 12:59"), 0, 6);
            calendario.add(new Label("13:00 - 13:59"), 0, 7);
            calendario.add(new Label("14:00 - 14:59"), 0, 8);
            calendario.add(new Label("15:00 - 15:59"), 0, 9);
            calendario.add(new Label("16:00 - 16:59"), 0, 10);
            calendario.add(new Label("17:00 - 17:59"), 0, 11);
            calendario.add(new Label("18:00 - 18:59"), 0, 12);
            calendario.add(new Label("19:00 - 19:59"), 0, 13);
            calendario.add(new Label("20:00 - 20:59"), 0, 14);
            calendario.add(new Label("21:00 - 21:59"), 0, 15);

            for (HorarioData horario : horarios) {
                int columna = horario.diaSemana() + 1;
                int fila = horario.horaInicio().getHour() - 6;

                VBox celda = new VBox();

                celda.getChildren().addAll(
                        new Label(horario.grupo().clave() + " - " + horario.grupo().nombre()),
                        new Label(horario.clase().materia().nombre()),
                        new Label(horario.clase().maestro() == null ? "Sin asignar" : horario.clase().maestro().nombre())
                );

                calendario.add(celda, columna, fila);
            }

            panelHorarios.getChildren().add(lblTitulo);
            panelHorarios.getChildren().add(calendario);
        }
    }

}