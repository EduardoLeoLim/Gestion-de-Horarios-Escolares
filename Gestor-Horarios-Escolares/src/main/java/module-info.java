module com.gamma.gestorhorariosescolares {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires sql2o;
    requires java.sql;

    opens com.gamma.gestorhorariosescolares to javafx.fxml;
    exports com.gamma.gestorhorariosescolares;
    exports com.gamma.gestorhorariosescolares.infrastructure.viewcontrollers.user;
    opens com.gamma.gestorhorariosescolares.infrastructure.viewcontrollers.user to javafx.fxml;
    exports com.gamma.gestorhorariosescolares.infrastructure.viewcontrollers;
    opens com.gamma.gestorhorariosescolares.infrastructure.viewcontrollers to javafx.fxml;
}