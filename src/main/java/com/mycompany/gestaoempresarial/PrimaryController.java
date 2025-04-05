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
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarClientesView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Editar Clientes");
        App.configurarJanela(stage, 900, 600);
        stage.setScene(new Scene(root));
        stage.sizeToScene(); // Ajusta o tamanho da janela ao conteúdo
        stage.show();
    }

    @FXML
    private void cadastrarProdutos() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarProdutosView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Produtos");
        App.configurarJanela(stage, 900, 600);
        stage.setScene(new Scene(root));
        stage.sizeToScene(); // Ajusta o tamanho da janela ao conteúdo
        stage.show();
    }

    @FXML
    private void gerenciarVendas() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarVendasView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Vendas");
        App.configurarJanela(stage, 900, 600);
        stage.setScene(new Scene(root));
        stage.sizeToScene(); // Ajusta o tamanho da janela ao conteúdo
        stage.show();
    }

    @FXML
    private void gerenciarFornecedores() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarFornecedoresView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Fornecedores");
        App.configurarJanela(stage, 900, 600);
        stage.setScene(new Scene(root));
        stage.sizeToScene(); // Ajusta o tamanho da janela ao conteúdo
        stage.show();
    }
    
    @FXML
    private void gerenciarRelatorios() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gerenciarRelatoriosView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Fornecedores");
        App.configurarJanela(stage, 900, 600);
        stage.setScene(new Scene(root));
        stage.sizeToScene(); // Ajusta o tamanho da janela ao conteúdo
        stage.show();
    }
}