module com.mycompany.gestaoempresarial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.gestaoempresarial to javafx.fxml;
    exports com.mycompany.gestaoempresarial;
    exports com.mycompany.gestaoempresarial.Usuarios;
    opens com.mycompany.gestaoempresarial.Usuarios to javafx.fxml;
}
