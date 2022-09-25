module com.gamma.gestorhorariosescolares {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires sql2o;
    requires java.sql;

    exports com.gamma.gestorhorariosescolares;
    opens com.gamma.gestorhorariosescolares to javafx.fxml;
    exports com.gamma.gestorhorariosescolares.infrastructure.user.viewcontrollers;
    opens com.gamma.gestorhorariosescolares.infrastructure.user.viewcontrollers to javafx.fxml;
}