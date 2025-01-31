package com.mycompany.gestaoempresarial;

import com.mycompany.gestaoempresarial.Usuarios.Cliente;
import example.DAO.ClientesDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.mycompany.gestaoempresarial.Usuarios.Segmento;
import java.sql.SQLException;

public class CadastrarClientesController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cpfCnpjField;
    @FXML
    private TextField enderecoField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField emailField;

    @FXML
    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String cpfCnpj = cpfCnpjField.getText();
        String endereco = enderecoField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        Segmento segmento = Segmento.Regular;

        Cliente cliente = new Cliente(nome, cpfCnpj, endereco, telefone, email, segmento);
        ClientesDAO dao = new ClientesDAO();

        try {
            dao.inserir(cliente);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Cliente cadastrado com sucesso!");
            alert.showAndWait();
        } catch (ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao cadastrar cliente!" + e);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println(e);
        }

        nomeField.clear();
        cpfCnpjField.clear();
        enderecoField.clear();
        telefoneField.clear();
        emailField.clear();

    }
}
