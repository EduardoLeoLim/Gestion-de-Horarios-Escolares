package com.gamma.gestorhorariosescolares.clase.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.clase.aplicacion.BuscarHorariosDisponibles;
import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.RecursoNoEncontradoException;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.conexiones.MySql2oConexiones;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.BuscarEdificios;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificioData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.EdificiosData;
import com.gamma.gestorhorariosescolares.edificio.aplicacion.buscar.BuscadorEdificio;
import com.gamma.gestorhorariosescolares.edificio.infrestructura.persistencia.MySql2oEdificioRepositorio;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorarioDisponibleData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.HorariosDisponiblesData;
import com.gamma.gestorhorariosescolares.horario.aplicacion.buscar.BuscadorHorario;
import com.gamma.gestorhorariosescolares.horario.infrestructura.persistencia.MySql2oHorarioRepositorio;
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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class HorariosDisponiblesControlador {

    private final Stage stage;
    private ObservableList<PeriodoEscolarData> coleccionPeriodosEscolares;
    private ObservableList<EdificioData> coleccionEdificios;
    private ObservableList<SalonData> coleccionSalones;

    @FXML
    private ComboBox<PeriodoEscolarData> cbxPeriodoEscolar;
    @FXML
    private ComboBox<EdificioData> cbxEdificio;
    @FXML
    private ComboBox<SalonData> cbxSalon;
    @FXML
    private VBox panelHorariosDisponibles;

    public HorariosDisponiblesControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        inicializarCbxPeriodoEscolar();
        inicializarCbxEdificio();
        inicializarCbxSalon();

        cargarPeriodosEscolares();
        cargarEdificios();
    }

    private void inicializarCbxPeriodoEscolar() {
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
    }

    private void cargarPeriodosEscolares() {
        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

            //Servicios
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarPeriodosEscolares buscarPeriodosEscolares = new BuscarPeriodosEscolares(buscadorPeriodoEscolar);
            PeriodosEscolaresData periodosEscolares = buscarPeriodosEscolares.buscarTodos();

            coleccionPeriodosEscolares.clear();
            coleccionPeriodosEscolares.addAll(periodosEscolares.periodosEscolares());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los periodos escolares");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarCbxEdificio() {
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
            protected void updateItem(EdificioData salon, boolean empty) {
                super.updateItem(salon, empty);
                setText("");
                if (empty)
                    return;

                setText(salon.clave() + " - " + salon.nombre());
            }
        });

        cbxEdificio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                cargarSalones(newValue);
        });
    }

    private void cargarEdificios() {
        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var edificioRepositorio = new MySql2oEdificioRepositorio(conexion);

            //Servicios
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);

            BuscarEdificios buscarEdificios = new BuscarEdificios(buscadorEdificio);
            EdificiosData edificios = buscarEdificios.buscarHabilitados();

            coleccionEdificios.clear();
            coleccionEdificios.addAll(edificios.edificios());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los edificios");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void inicializarCbxSalon() {
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

            coleccionSalones.clear();
            coleccionSalones.addAll(salones.salones());
        } catch (Sql2oException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los salones");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarHorariosDisponiblesClick() {
        PeriodoEscolarData periodoEscolarSeleccionado = cbxPeriodoEscolar.getValue();
        EdificioData edificioSeleccionado = cbxEdificio.getValue();
        SalonData salonSeleccionado = cbxSalon.getValue();

        if (!sonValidosDatosSeleccionados(periodoEscolarSeleccionado, edificioSeleccionado))
            return;

        List<SalonData> salones = new ArrayList<>();

        if (salonSeleccionado != null) {
            salones.add(salonSeleccionado);
        } else {
            salones.addAll(coleccionSalones);
        }

        panelHorariosDisponibles.getChildren().clear();
        Runnable runnable = () -> buscarHorariosDisponiblesDeSalon(periodoEscolarSeleccionado, edificioSeleccionado, salones);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private boolean sonValidosDatosSeleccionados(PeriodoEscolarData periodoEscolar, EdificioData edificio) {
        if (periodoEscolar == null || edificio == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al buscar los horarios disponibles");
            alert.setContentText("Debe seleccionar un periodo escolar y un edificio");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void buscarHorariosDisponiblesDeSalon(PeriodoEscolarData periodoEscolar, EdificioData edificio, List<SalonData> salones) {
        Sql2o conexionSql2o = MySql2oConexiones.getConexionPrimaria();

        try (Connection conexion = conexionSql2o.open()) {
            //Repositorios
            var horarioRepositorio = new MySql2oHorarioRepositorio(conexion);
            var edificioRepositorio = new MySql2oEdificioRepositorio(conexion);
            var salonRepositorio = new MySql2oSalonRepositorio(conexion);
            var periodoEscolarRepositorio = new MySql2oPeriodoEscolarRepositorio(conexion);

            //Servicios
            var buscadorHorario = new BuscadorHorario(horarioRepositorio);
            var buscadorEdificio = new BuscadorEdificio(edificioRepositorio);
            var buscadorSalon = new BuscadorSalon(salonRepositorio);
            var buscadorPeriodoEscolar = new BuscadorPeriodoEscolar(periodoEscolarRepositorio);

            BuscarHorariosDisponibles buscarHorariosDisponibles = new BuscarHorariosDisponibles(buscadorHorario,
                    buscadorEdificio, buscadorSalon, buscadorPeriodoEscolar);

            salones.forEach(salon -> {
                try {
                    HorariosDisponiblesData horariosDisponibles = buscarHorariosDisponibles.buscar(periodoEscolar.id(), salon.id());
                    Platform.runLater(() -> agregarHorariosDisponibles(salon, horariosDisponibles));
                } catch (RecursoNoEncontradoException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (RuntimeException ex) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al buscar los horarios disponibles");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            });
        }
    }

    private void agregarHorariosDisponibles(SalonData salon, HorariosDisponiblesData horariosDisponibles) {

        //Título
        Label lblTitulo = new Label();
        lblTitulo.setText(salon.clave() + " - " + salon.edificio().nombre());
        lblTitulo.getStyleClass().addAll("h5", "b");
        lblTitulo.setPadding(new Insets(15, 0, 5, 0));
        panelHorariosDisponibles.getChildren().add(lblTitulo);

        //Tabla
        ObservableList<HorarioDisponibleData> horariosDisponiblesObservable = FXCollections.observableArrayList(horariosDisponibles.horarios());

        TableView<HorarioDisponibleData> tablaHorariosDisponibles = new TableView<>();
        tablaHorariosDisponibles.setEditable(false);
        tablaHorariosDisponibles.setMaxHeight(200);
        tablaHorariosDisponibles.setPrefHeight(200);
        tablaHorariosDisponibles.getStyleClass().addAll("h5");

        TableColumn<HorarioDisponibleData, String> columnaDia = new TableColumn<>("Dia");
        columnaDia.setCellValueFactory(ft -> {
            String dia;
            switch (ft.getValue().diaSemana()) {
                case 0 -> dia = "Lunes";
                case 1 -> dia = "Martes";
                case 2 -> dia = "Miércoles";
                case 3 -> dia = "Jueves";
                case 4 -> dia = "Viernes";
                default -> dia = "";
            }
            return new SimpleStringProperty(dia);
        });

        TableColumn<HorarioDisponibleData, String> columnaHoraInicio = new TableColumn<>("Hora inicio");
        columnaHoraInicio.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().horaInicio()));
        columnaHoraInicio.setMinWidth(80);

        TableColumn<HorarioDisponibleData, String> columnaHoraFin = new TableColumn<>("Hora fin");
        columnaHoraFin.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().horaFin()));
        columnaHoraFin.setMinWidth(80);

        TableColumn<HorarioDisponibleData, String> columnaSalon = new TableColumn<>("Salon");
        columnaSalon.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().salon().clave()));
        columnaSalon.setMinWidth(80);

        TableColumn<HorarioDisponibleData, String> columnaEdificio = new TableColumn<>("Edificio");
        columnaEdificio.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().salon().edificio().nombre()));
        columnaEdificio.setMinWidth(80);

        TableColumn<HorarioDisponibleData, String> columnaCapacidad = new TableColumn<>("Capacidad");
        columnaCapacidad.setCellValueFactory(ft -> new SimpleStringProperty(ft.getValue().salon().capacidad() + " alumnos"));
        columnaCapacidad.setMinWidth(80);

        TableColumn<HorarioDisponibleData, String> columnaAsignar = new TableColumn<>();
        columnaAsignar.setMinWidth(80);
        columnaAsignar.setMaxWidth(80);
        columnaAsignar.setCellFactory(param -> new TableCell<>() {
            private final Button btnAsignar = new Button("Asignar");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                btnAsignar.getStyleClass().addAll("btn-primary", "b");
                btnAsignar.setOnAction(event -> {
                    HorarioDisponibleData horarioDisponible = getTableView().getItems().get(getIndex());
                    asignarHorario(horarioDisponible);
                });

                setGraphic(btnAsignar);
            }
        });

        tablaHorariosDisponibles.getColumns().addAll(columnaDia, columnaHoraInicio, columnaHoraFin, columnaEdificio,
                columnaSalon, columnaCapacidad);

        tablaHorariosDisponibles.setItems(horariosDisponiblesObservable);
        panelHorariosDisponibles.getChildren().add(tablaHorariosDisponibles);
    }

    private void asignarHorario(HorarioDisponibleData horarioDisponible) {

    }
}
