module com.gamma.gestorhorariosescolares {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.crypto.ec;
    requires org.kordamp.bootstrapfx.core;
    requires sql2o;

    exports com.gamma.gestorhorariosescolares;
    opens com.gamma.gestorhorariosescolares.administrador.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.alumno.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.clase.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.edificio.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.grupo.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.inscripcion.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.maestro.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.materia.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.periodoescolar.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.planestudio.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.salon.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.secretario.infrestructura.controladores to javafx.fxml;
    opens com.gamma.gestorhorariosescolares.usuario.infrestructura.controladores to javafx.fxml;
}