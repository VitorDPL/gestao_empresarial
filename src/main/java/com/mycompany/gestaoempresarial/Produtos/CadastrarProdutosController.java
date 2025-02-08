package com.mycompany.gestaoempresarial.Produtos;

import example.DAO.CategoriasDAO;
import example.DAO.ProdutosDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CadastrarProdutosController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField precoCompraField;

    @FXML
    private TextField precoVendaField;

    @FXML
    private ComboBox<String> categoriaComboBox;

    @FXML
    private void initialize() {
        try {
            CategoriasDAO categoriasDAO = new CategoriasDAO();
            ArrayList<Categoria> categorias = categoriasDAO.buscarTodos(); 

            // Adicionar as categorias ao ComboBox
            for (Categoria categoria : categorias) {
                categoriaComboBox.getItems().add(categoria.getNome()); 
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao carregar categorias: " + e.getMessage());
            alert.showAndWait();
        }
    }


    
    @FXML
    private void cadastrarProduto() {
        try {
            String nome = nomeField.getText();
            String codigo = codigoField.getText();
            String descricao = descricaoField.getText();
            double precoCompra = Double.parseDouble(precoCompraField.getText());
            double precoVenda = Double.parseDouble(precoVendaField.getText());
            int categoriaId = categoriaComboBox.getSelectionModel().getSelectedIndex() + 1; 

            Produto produto = new Produto(0, nome, codigo, descricao, categoriaId, (int)precoCompra, precoVenda, 0, 0, 0); 
            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.inserir(produto);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Produto cadastrado com sucesso!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar produto: " + e.getMessage());
            System.out.println("Erro" + e);
            alert.showAndWait();
        }
    }
}