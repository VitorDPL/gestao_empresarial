package com.mycompany.gestaoempresarial;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void editarClientes() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarClientesView.fxml"));

        // Criar uma nova janela (Stage)
        Stage stage = new Stage();
        stage.setTitle("Editar Clientes");

        // Configurar a janela com a função reutilizável da classe App
        App.configurarJanela(stage, 900, 600);

        // Configurar a cena e mostrar a nova janela
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void cadastrarProdutos() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarProdutosView.fxml"));

        // Criar uma nova janela (Stage)
        Stage stage = new Stage();
        stage.setTitle("Editar Clientes");

        // Configurar a janela com a função reutilizável da classe App
        App.configurarJanela(stage, 900, 600);

        // Configurar a cena e mostrar a nova janela
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void gerenciarVendas() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarVendasView.fxml"));

        // Criar uma nova janela (Stage)
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Vendas");

        // Configurar a janela com a função reutilizável da classe App
        App.configurarJanela(stage, 900, 600);

        // Configurar a cena e mostrar a nova janela
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void gerenciarFornecedores() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(("gerenciarFornecedoresView.fxml")));

        Stage stage = new Stage();
        stage.setTitle("Gerenciar Fornecedores");

        App.configurarJanela(stage, 900, 600);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
