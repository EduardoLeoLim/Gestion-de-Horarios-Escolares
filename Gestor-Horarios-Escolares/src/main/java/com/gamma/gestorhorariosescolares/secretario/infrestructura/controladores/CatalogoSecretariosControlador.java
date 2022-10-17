package com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;

import com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias.Temporizador;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CatalogoSecretariosControlador {
    private final Stage stage;
    private Temporizador temporizadorBusqueda;


    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView tablaAdministradores;

    public CatalogoSecretariosControlador(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Configuración de busqueda
        temporizadorBusqueda = new Temporizador(1, (temporizador) -> {
            //La funcion es llamada cuando se agota el tiempo
            buscarSecretarios(txtBuscar.getText().trim());
        });
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.trim().equals(newValue.trim()))//No se realiza la busqueda cuando se presionan teclas que no modifican la cadena de búsqueda.
                return;
            temporizadorBusqueda.reiniciar();
        });
    }

    private void buscarSecretarios(String criterioBuscqueda) {
        System.out.println("Busqueda de secretarios por criterio: '" + criterioBuscqueda + "'");
    }

    public void liberarRecursos() {
        if (temporizadorBusqueda != null)
            temporizadorBusqueda.cancel();
    }
}
