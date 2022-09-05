module com.gamma.gestorhorariosescolares {
    requires javafx.controls;
    requires javafx.fxml;
    requires sql2o;
    requires java.sql;

    opens com.gamma.gestorhorariosescolares to javafx.fxml;
    exports com.gamma.gestorhorariosescolares;
    exports com.gamma.gestorhorariosescolares.infrastructure.user.controllers;
    opens com.gamma.gestorhorariosescolares.infrastructure.user.controllers to javafx.fxml;
}