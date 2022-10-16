package com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CatalogoAdministradoresControlador {
    private final Stage stage;
    private Temporizador temporizadorBusqueda;


    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView tablaAdministradores;

    public CatalogoAdministradoresControlador(Stage stage) {
        if (stage == null)
            throw new NullPointerException();

        this.stage = stage;
        stage.setOnHiding(event -> {
            temporizadorBusqueda.cancel();
        });
    }

    @FXML
    public void initialize() {

        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarAdministradores(txtBuscar.getText());
        });
        txtBuscar.setOnKeyReleased(event -> {
            temporizadorBusqueda.reiniciar();
        });
    }

    private void buscarAdministradores(String criterioBuscqueda) {
        System.out.println("Busqueda de administradores por criterio: " + criterioBuscqueda);
    }
}
