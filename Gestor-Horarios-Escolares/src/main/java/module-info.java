module com.gamma.gestorhorariosescolares {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires sql2o;

    exports com.gamma.gestorhorariosescolares;
    opens com.gamma.gestorhorariosescolares to javafx.fxml;
    exports com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores;
    opens com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores to javafx.fxml;
    exports com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores;
    opens com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores to javafx.fxml;
    exports com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores;
    opens com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores to javafx.fxml;
}