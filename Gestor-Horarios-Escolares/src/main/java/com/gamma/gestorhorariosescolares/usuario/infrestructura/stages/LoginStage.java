package com.gamma.gestorhorariosescolares.usuario.infrestructura.stages;

import com.gamma.gestorhorariosescolares.App;
import com.gamma.gestorhorariosescolares.compartido.infrestructura.stage.CustomStage;
import com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores.LoginControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class LoginStage extends CustomStage {
    public LoginStage() {
        setTitle("Iniciar sesión ");
        setResizable(false);
        try {
            FXMLLoader root = new FXMLLoader(App.class.getResource("usuario/infrestructura/vistas/Login.fxml"));
            LoginControlador loginController = new LoginControlador();
            root.setController(loginController);
            root.load();
            Parent conntent = root.getRoot();
            conntent.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            setContent(conntent);
        } catch (IOException e) {
            System.err.println("Error al cargar ventana.");
            System.err.println(e.getMessage());
        }
    }
}
