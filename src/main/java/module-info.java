module com.mycompany.gestaoempresarial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires java.logging;

    // Abrir os pacotes necessários para o javafx.fxml
    opens com.mycompany.gestaoempresarial to javafx.fxml;
    opens com.mycompany.gestaoempresarial.clientes to javafx.fxml;
    opens com.mycompany.gestaoempresarial.Produtos to javafx.fxml;  // ADICIONADO O PACOTE PRODUTOS

    // Exportar os pacotes necessários
    exports com.mycompany.gestaoempresarial;
    exports com.mycompany.gestaoempresarial.clientes;
    exports com.mycompany.gestaoempresarial.Produtos; // ADICIONADO O PACOTE PRODUTOS
}
