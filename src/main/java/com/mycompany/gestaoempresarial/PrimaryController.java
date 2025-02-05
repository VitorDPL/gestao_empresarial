package com.mycompany.gestaoempresarial;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void cadastrarCliente() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("cadastrarClientesView.fxml"));
        
        // Criar uma nova janela (Stage)
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Cliente");

        // Configurar a janela com a função reutilizável da classe App
        App.configurarJanela(stage, 900, 600);

        // Configurar a cena e mostrar a nova janela
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void exibirClientes() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("exibirClientesView.fxml"));
        
        // Criar uma nova janela (Stage)
        Stage stage = new Stage();
        stage.setTitle("Exibir Clientes");

        // Configurar a janela com a função reutilizável da classe App
        App.configurarJanela(stage, 1200, 600);

        // Configurar a cena e mostrar a nova janela
        stage.setScene(new Scene(root));
        stage.show();
    }
  
    @FXML
    private void editarClientes() throws IOException {
        // Carregar o FXML da nova tela
        Parent root = FXMLLoader.load(getClass().getResource("editarClientesView.fxml"));

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
    private void deletarClientes() throws IOException {
        System.out.println("Clicou em deletar");
    }
}
