package com.mycompany.gestaoempresarial.Fornecedores;

import com.mycompany.gestaoempresarial.Produtos.Produto;
import example.DAO.ProdutosFornecidosDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class ProdutosFornecidosPorFornecedorController {
    @FXML
    private TableView<Produto> tabelaProdutosFornecidos;

    @FXML
    private TableColumn<Produto, String> colunaCodigoProduto;
    @FXML
    private TableColumn<Produto, String> colunaNomeProduto;
    @FXML
    private TableColumn<Produto, String> colunaDescricaoProduto;
    @FXML
    private TableColumn<Produto, String> colunaCategoriaProduto;
    @FXML
    private TableColumn<Produto, Double> colunaPrecoCompraProduto;
    @FXML
    private TableColumn<Produto, Double> colunaPrecoVendaProduto;
    @FXML
    private TableColumn<Produto, Double> colunaLucroProduto;
    @FXML
    private TableColumn<Produto, Double> colunaCustoProduto;
    @FXML
    private TableColumn<Produto, Integer> colunaEstoqueAtualProduto;

    private Fornecedor fornecedor;

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        carregarProdutosFornecidos();
    }

    private void carregarProdutosFornecidos() {
        try {
            ProdutosFornecidosDAO produtosFornecidosDAO = new ProdutosFornecidosDAO();
            List<Produto> produtosFornecidos = produtosFornecidosDAO.buscarPorFornecedorId(fornecedor.getId());
            tabelaProdutosFornecidos.setItems(FXCollections.observableArrayList(produtosFornecidos));
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar alerta de erro
        }
    }

    @FXML
    public void initialize() {
        colunaCodigoProduto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDescricaoProduto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaCategoriaProduto.setCellValueFactory(new PropertyValueFactory<>("categoria_id"));
        colunaPrecoCompraProduto.setCellValueFactory(new PropertyValueFactory<>("preco_compra"));
        colunaPrecoVendaProduto.setCellValueFactory(new PropertyValueFactory<>("preco_venda"));
        colunaLucroProduto.setCellValueFactory(new PropertyValueFactory<>("lucro_produto"));
        colunaCustoProduto.setCellValueFactory(new PropertyValueFactory<>("custo"));
        colunaEstoqueAtualProduto.setCellValueFactory(new PropertyValueFactory<>("estoque_atual"));
    }
}